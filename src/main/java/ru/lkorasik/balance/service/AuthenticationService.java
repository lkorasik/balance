package ru.lkorasik.balance.service;

import ru.lkorasik.balance.api.auth.LoginRequestDto;

public interface AuthenticationService {
    String authenticate(LoginRequestDto input);
}
