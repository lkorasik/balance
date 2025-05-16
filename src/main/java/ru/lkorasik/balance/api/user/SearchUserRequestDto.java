package ru.lkorasik.balance.api.user;

import java.time.LocalDate;

public record SearchUserRequestDto(
        PageRequestDto page,
        LocalDate dateOfBirth,
        String phone,
        String name,
        String email
) {
}
