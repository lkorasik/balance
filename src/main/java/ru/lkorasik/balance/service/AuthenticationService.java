package ru.lkorasik.balance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.lkorasik.balance.api.auth.LoginRequestDto;
import ru.lkorasik.balance.data.entity.User;
import ru.lkorasik.balance.data.repository.UserRepository;

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

        User user = userRepository.findByEmail(input.email()).orElseThrow();
        String jwt = jwtService.generateToken(user);

        return jwt;
    }
}