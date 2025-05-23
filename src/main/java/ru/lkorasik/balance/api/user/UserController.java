package ru.lkorasik.balance.api.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.lkorasik.balance.configuration.RedisConfiguration;
import ru.lkorasik.balance.data.entity.User;
import ru.lkorasik.balance.exceptions.UnauthorizedUserChangeException;
import ru.lkorasik.balance.service.Mapper;
import ru.lkorasik.balance.service.UserService;

import java.time.LocalDate;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final Mapper mapper;

    @Autowired
    public UserController(UserService userService, Mapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @CacheEvict(value = RedisConfiguration.USER_INFO, key = "#id")
    @PostMapping("/{id}/email")
    public void addEmail(@PathVariable("id") long id, @Valid @RequestBody AddEmailRequestDto dto) {
        User user = getCurrentUser(id);
        userService.addEmail(user, dto.emails());
    }

    @CacheEvict(value = RedisConfiguration.USER_INFO, key = "#id")
    @PutMapping("/{id}/email")
    public void updateEmail(@PathVariable("id") long id, @Valid @RequestBody UpdateEmailsListRequestDto dto) {
        User user = getCurrentUser(id);
        userService.updateEmails(user, dto.emails());
    }

    @CacheEvict(value = RedisConfiguration.USER_INFO, key = "#id")
    @DeleteMapping("/{id}/email")
    public void deleteEmail(@PathVariable("id") long id, @Valid @RequestBody DeleteEmailsRequestDto dto) {
        User user = getCurrentUser(id);
        userService.deleteEmails(user, dto.emailIds());
    }

    @CacheEvict(value = RedisConfiguration.USER_INFO, key = "#id")
    @PostMapping("/{id}/phone")
    public void addPhone(@PathVariable("id") long id, @Valid @RequestBody AddPhoneRequestDto dto) {
        User user = getCurrentUser(id);
        userService.addPhone(user, dto.phones());
    }

    @CacheEvict(value = RedisConfiguration.USER_INFO, key = "#id")
    @PutMapping("/{id}/phone")
    public void updatePhone(@PathVariable("id") long id, @Valid @RequestBody UpdatePhonesListRequestDto dto) {
        User user = getCurrentUser(id);
        userService.updatePhones(user, dto.phones());
    }

    @CacheEvict(value = RedisConfiguration.USER_INFO, key = "#id")
    @DeleteMapping("/{id}/phone")
    public void deletePhone(@PathVariable("id") long id, @Valid @RequestBody DeletePhonesRequestDto dto) {
        User user = getCurrentUser(id);
        userService.deletePhones(user, dto.phoneIds());
    }

    @Cacheable(value = RedisConfiguration.USER_INFO, key = "#id")
    @GetMapping("/{id}")
    public UserResponseDto getUser(@PathVariable("id") long id) {
        User user = getCurrentUser(id);
        return mapper.map(user);
    }

    @Cacheable(
            value = RedisConfiguration.USER_SEARCH,
            key = "#email + ':' + #phone + ':' + #name + ':' + #dateOfBirth + ':' + #pageSize + ':' + #pageNumber"
    )
    @GetMapping("/all")
    public List<UserResponseDto> searchUsers(
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "dateOfBirth", required = false) LocalDate dateOfBirth,
            @Positive @RequestParam(value = "pageSize") int pageSize,
            @PositiveOrZero @RequestParam(value = "pageNumber") int pageNumber
    ) {
        PageRequestDto page = new PageRequestDto(pageSize, pageNumber);
        SearchUserRequestDto dto = new SearchUserRequestDto(page, dateOfBirth, phone, name, email);

        return userService.searchUsers(dto)
                .stream()
                .map(mapper::map)
                .toList();
    }

    private User getCurrentUser(long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.getId() != id) {
            throw new UnauthorizedUserChangeException(user.getId(), id);
        }
        return user;
    }
}
