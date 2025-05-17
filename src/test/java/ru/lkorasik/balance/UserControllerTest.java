package ru.lkorasik.balance;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.lkorasik.balance.api.auth.LoginRequestDto;
import ru.lkorasik.balance.api.auth.LoginResponseDto;
import ru.lkorasik.balance.api.exception.ExceptionDto;
import ru.lkorasik.balance.api.exception.ValidationExceptionDto;
import ru.lkorasik.balance.api.user.AddPhoneRequestDto;
import ru.lkorasik.balance.data.entity.Account;
import ru.lkorasik.balance.data.entity.EmailData;
import ru.lkorasik.balance.data.entity.PhoneData;
import ru.lkorasik.balance.data.entity.User;
import ru.lkorasik.balance.data.repository.UserRepository;
import ru.lkorasik.balance.filter.JwtAuthenticationFilter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @LocalServerPort
    private Integer port;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>( "postgres:16-alpine");

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private ObjectMapper mapper;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                .addFilter(jwtAuthenticationFilter)
                .build();
    }

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void addPhoneNumber() throws Exception {
        Account account = new Account();
        account.setId(1L);
        PhoneData phoneData = new PhoneData();
        String phone = "89220000000";
        String newPhone = "89220000001";
        phoneData.setPhone(phone);
        EmailData emailData = new EmailData();
        String email = "q@q.q";
        emailData.setEmail(email);
        User user = new User();
        user.setName(UUID.randomUUID().toString());
        user.setDateOfBirth(LocalDate.of(2000, 1, 1));
        String password = "123";
        user.setPassword(passwordEncoder.encode(password));
        user.setAccount(account);
        account.setUser(user);
        user.addEmail(emailData);
        user.addPhone(phoneData);

        userRepository.save(user);

        LoginRequestDto loginRequestDto = new LoginRequestDto(email, null, password);

        String json = mapper.writeValueAsString(loginRequestDto);

        json = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        LoginResponseDto responseDto = mapper.readValue(json, LoginResponseDto.class);

        AddPhoneRequestDto dto = new AddPhoneRequestDto(List.of(newPhone));

        json = mapper.writeValueAsString(dto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/" + user.getId() + "/phone")
                        .header("Authorization", "Bearer " + responseDto.jwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());

        user = userRepository.findById(user.getId())
                .orElseThrow();

        List<String> userPhones = user.getPhones().stream().map(PhoneData::getPhone).toList();
        Assertions.assertEquals(2, userPhones.size());
        Assertions.assertTrue(userPhones.contains(phone));
        Assertions.assertTrue(userPhones.contains(newPhone));
    }

    @Test
    public void addIncorrectPhoneNumber() throws Exception {
        Account account = new Account();
        account.setId(1L);
        PhoneData phoneData = new PhoneData();
        String phone = "89220000000";
        String newPhone = "abcde";
        phoneData.setPhone(phone);
        EmailData emailData = new EmailData();
        String email = "q@q.q";
        emailData.setEmail(email);
        User user = new User();
        user.setName(UUID.randomUUID().toString());
        user.setDateOfBirth(LocalDate.of(2000, 1, 1));
        String password = "123";
        user.setPassword(passwordEncoder.encode(password));
        user.setAccount(account);
        account.setUser(user);
        user.addEmail(emailData);
        user.addPhone(phoneData);

        userRepository.save(user);

        LoginRequestDto loginRequestDto = new LoginRequestDto(email, null, password);

        String json = mapper.writeValueAsString(loginRequestDto);

        json = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        LoginResponseDto responseDto = mapper.readValue(json, LoginResponseDto.class);

        AddPhoneRequestDto dto = new AddPhoneRequestDto(
                List.of(newPhone)
        );

        json = mapper.writeValueAsString(dto);

        json = mockMvc.perform(MockMvcRequestBuilders.post("/api/user/" + user.getId() + "/phone")
                        .header("Authorization", "Bearer " + responseDto.jwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().is(500))
                .andReturn()
                .getResponse()
                .getContentAsString();

        ExceptionDto exceptionDto = mapper.readValue(json, ExceptionDto.class);

        Assertions.assertNotNull(exceptionDto.message());

        user = userRepository.findById(user.getId())
                .orElseThrow();

        List<String> userPhones = user.getPhones().stream().map(PhoneData::getPhone).toList();
        Assertions.assertEquals(1, userPhones.size());
        Assertions.assertTrue(userPhones.contains(phone));
    }

    @Test
    public void addNoPhoneNumber() throws Exception {
        Account account = new Account();
        account.setId(1L);
        PhoneData phoneData = new PhoneData();
        String phone = "89220000000";
        phoneData.setPhone(phone);
        EmailData emailData = new EmailData();
        String email = "q@q.q";
        emailData.setEmail(email);
        User user = new User();
        user.setName(UUID.randomUUID().toString());
        user.setDateOfBirth(LocalDate.of(2000, 1, 1));
        String password = "123";
        user.setPassword(passwordEncoder.encode(password));
        user.setAccount(account);
        account.setUser(user);
        user.addEmail(emailData);
        user.addPhone(phoneData);

        userRepository.save(user);

        LoginRequestDto loginRequestDto = new LoginRequestDto(email, null, password);

        String json = mapper.writeValueAsString(loginRequestDto);

        json = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        LoginResponseDto responseDto = mapper.readValue(json, LoginResponseDto.class);

        AddPhoneRequestDto dto = new AddPhoneRequestDto(null);

        json = mapper.writeValueAsString(dto);

        json = mockMvc.perform(MockMvcRequestBuilders.post("/api/user/" + user.getId() + "/phone")
                        .header("Authorization", "Bearer " + responseDto.jwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().is(500))
                .andReturn()
                .getResponse()
                .getContentAsString();

        ValidationExceptionDto exceptionDto = mapper.readValue(json, ValidationExceptionDto.class);

        Assertions.assertNotNull(exceptionDto);
        Assertions.assertNotNull(exceptionDto.errors());

        user = userRepository.findById(user.getId())
                .orElseThrow();

        List<String> userPhones = user.getPhones().stream().map(PhoneData::getPhone).toList();
        Assertions.assertEquals(1, userPhones.size());
        Assertions.assertTrue(userPhones.contains(phone));
    }

    @Test
    public void addEmptyListPhoneNumber() throws Exception {
        Account account = new Account();
        account.setId(1L);
        PhoneData phoneData = new PhoneData();
        String phone = "89220000000";
        String newPhone = "abcde";
        phoneData.setPhone(phone);
        EmailData emailData = new EmailData();
        String email = "q@q.q";
        emailData.setEmail(email);
        User user = new User();
        user.setName(UUID.randomUUID().toString());
        user.setDateOfBirth(LocalDate.of(2000, 1, 1));
        String password = "123";
        user.setPassword(passwordEncoder.encode(password));
        user.setAccount(account);
        account.setUser(user);
        user.addEmail(emailData);
        user.addPhone(phoneData);

        userRepository.save(user);

        LoginRequestDto loginRequestDto = new LoginRequestDto(email, null, password);

        String json = mapper.writeValueAsString(loginRequestDto);

        json = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        LoginResponseDto responseDto = mapper.readValue(json, LoginResponseDto.class);

        AddPhoneRequestDto dto = new AddPhoneRequestDto(List.of());

        json = mapper.writeValueAsString(dto);

        json = mockMvc.perform(MockMvcRequestBuilders.post("/api/user/" + user.getId() + "/phone")
                        .header("Authorization", "Bearer " + responseDto.jwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().is(500))
                .andReturn()
                .getResponse()
                .getContentAsString();

        ValidationExceptionDto exceptionDto = mapper.readValue(json, ValidationExceptionDto.class);

        Assertions.assertNotNull(exceptionDto);
        Assertions.assertNotNull(exceptionDto.errors());

        user = userRepository.findById(user.getId())
                .orElseThrow();

        List<String> userPhones = user.getPhones().stream().map(PhoneData::getPhone).toList();
        Assertions.assertEquals(1, userPhones.size());
        Assertions.assertTrue(userPhones.contains(phone));
    }
}
