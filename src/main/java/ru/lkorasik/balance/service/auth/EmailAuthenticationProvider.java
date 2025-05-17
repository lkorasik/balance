package ru.lkorasik.balance.service.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.lkorasik.balance.service.UserService;

@Component
public class EmailAuthenticationProvider implements AuthenticationProvider {
    private static final Logger log = LoggerFactory.getLogger(EmailAuthenticationProvider.class);
    private final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

    @Autowired
    public EmailAuthenticationProvider(UserService userService, PasswordEncoder passwordEncoder) {
        authProvider.setUserDetailsService(userService::loadByEmail);
        authProvider.setPasswordEncoder(passwordEncoder);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("Try to authenticate via email");
        return authProvider.authenticate(authentication);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(EmailAuthenticationToken.class);
    }
}
