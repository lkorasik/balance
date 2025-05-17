package ru.lkorasik.balance.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.lkorasik.balance.api.auth.LoginRequestDto;
import ru.lkorasik.balance.data.entity.User;
import ru.lkorasik.balance.data.repository.UserRepository;
import ru.lkorasik.balance.exceptions.*;
import ru.lkorasik.balance.service.AuthenticationService;
import ru.lkorasik.balance.service.JwtService;
import ru.lkorasik.balance.service.auth.EmailAuthenticationToken;
import ru.lkorasik.balance.service.auth.PhoneAuthenticationToken;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Autowired
    public AuthenticationServiceImpl(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            JwtServiceImpl jwtService
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    public String authenticate(LoginRequestDto input) {
        assertCorrectAuthenticationRequest(input);

        Authentication authentication = buildAuthentication(input);
        authenticationManager.authenticate(authentication);

        User user = getUser(input);

        return jwtService.generateToken(user);
    }

    private void assertCorrectAuthenticationRequest(LoginRequestDto input) {
        if ((input.email() != null) && (input.phone() != null)) {
            throw new UseSingleUserIdentifierException();
        }

        if ((input.email() == null) && (input.phone() == null)) {
            throw new NoUserIdentifierException();
        }
    }

    private Authentication buildAuthentication(LoginRequestDto input) {
        if (input.email() != null) {
            return new EmailAuthenticationToken(input.email(), input.password());
        } else {
            return new PhoneAuthenticationToken(input.phone(), input.password());
        }
    }

    private User getUser(LoginRequestDto input) {
        if (input.email() != null) {
            return userRepository.findByEmail(input.email()).orElseThrow(() -> new UserNotFoundByEmailException(input.email()));
        } else {
            return userRepository.findByPhone(input.phone()).orElseThrow(() -> new UserNotFoundByPhoneException(input.phone()));
        }
    }
}