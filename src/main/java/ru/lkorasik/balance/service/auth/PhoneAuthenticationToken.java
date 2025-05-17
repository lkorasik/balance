package ru.lkorasik.balance.service.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class PhoneAuthenticationToken extends UsernamePasswordAuthenticationToken {
    public PhoneAuthenticationToken(String phone, String password) {
        super(phone, password);
    }
}
