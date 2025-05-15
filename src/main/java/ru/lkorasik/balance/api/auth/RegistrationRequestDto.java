package ru.lkorasik.balance.api.auth;


public record RegistrationRequestDto(
        String email,
        String password
) {
}
