package ru.lkorasik.balance.api.user;

public record UpdateEmailRequestDto(
        long id,
        String email
) {
}
