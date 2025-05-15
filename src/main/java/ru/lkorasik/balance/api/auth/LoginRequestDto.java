package ru.lkorasik.balance.api.auth;


public record LoginRequestDto(
        String email,
        String password
) {
}
