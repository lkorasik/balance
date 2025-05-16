package ru.lkorasik.balance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.lkorasik.balance.api.auth.LoginRequestDto;
import ru.lkorasik.balance.data.entity.User;
import ru.lkorasik.balance.data.repository.UserRepository;
import ru.lkorasik.balance.exceptions.UserNotFoundByEmailException;
import ru.lkorasik.balance.exceptions.UserNotFoundByPhoneException;
import ru.lkorasik.balance.exceptions.UserNotFoundException;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Autowired
    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public String authenticate(LoginRequestDto input) {
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