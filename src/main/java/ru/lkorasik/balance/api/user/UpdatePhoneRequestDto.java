package ru.lkorasik.balance.api.user;

public record UpdatePhoneRequestDto(
        long id,
        String phone
) {}
