package ru.lkorasik.balance.api.auth;


import jakarta.validation.constraints.Size;

public record LoginRequestDto(
        String email,
        String phone,
        @Size(min = 3)
        String password
) {
}
