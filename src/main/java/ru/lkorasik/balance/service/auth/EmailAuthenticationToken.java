package ru.lkorasik.balance.service.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class EmailAuthenticationToken extends UsernamePasswordAuthenticationToken {
    public EmailAuthenticationToken(String email, String password) {
        super(email, password);
    }
}
