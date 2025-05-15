package ru.lkorasik.balance.api.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.lkorasik.balance.service.AuthenticationService;
import ru.lkorasik.balance.service.UserService;

@Validated
@RestController
@RequestMapping("/api/auth")
public class AuthControllerImpl {
    private static final Logger log = LoggerFactory.getLogger(AuthControllerImpl.class);

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthControllerImpl(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto dto) {
        log.info(String.valueOf(dto));
        String jwt = authenticationService.authenticate(dto);
        return new LoginResponseDto(jwt);
    }
}
