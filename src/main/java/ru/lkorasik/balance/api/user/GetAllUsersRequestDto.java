package ru.lkorasik.balance.api.user;

import java.time.LocalDate;

public record GetAllUsersRequestDto(
        LocalDate dateOfBirth,
        String phone,
        String name,
        String email,
        int pageSize,
        int page
) {
}
