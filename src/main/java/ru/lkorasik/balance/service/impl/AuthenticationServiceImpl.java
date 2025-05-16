package ru.lkorasik.balance.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.lkorasik.balance.api.auth.LoginRequestDto;
import ru.lkorasik.balance.data.entity.User;
import ru.lkorasik.balance.data.repository.UserRepository;
import ru.lkorasik.balance.exceptions.UseSingleUserIdentifierException;
import ru.lkorasik.balance.exceptions.UserNotFoundByEmailException;
import ru.lkorasik.balance.exceptions.UserNotFoundByPhoneException;
import ru.lkorasik.balance.exceptions.UserNotFoundException;
import ru.lkorasik.balance.service.AuthenticationService;
import ru.lkorasik.balance.service.JwtService;

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
        if ((input.email() != null) && (input.phone() != null)) {
            throw new UseSingleUserIdentifierException();
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(input.email(), input.password());
        authenticationManager.authenticate(token);

        User user = null;
        if (input.email() != null) {
            user = userRepository.findByEmail(input.email()).orElseThrow(() -> new UserNotFoundByEmailException(input.email()));
        } else if (input.phone() != null) {
            user = userRepository.findByPhone(input.phone()).orElseThrow(() -> new UserNotFoundByPhoneException(input.phone()));
        } else {
            throw new UserNotFoundException("User not found");
        }

        return jwtService.generateToken(user);
    }
}