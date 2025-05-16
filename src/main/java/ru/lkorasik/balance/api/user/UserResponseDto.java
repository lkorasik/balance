package ru.lkorasik.balance.api.user;

import java.time.LocalDate;
import java.util.List;

public record UserResponseDto(
        long id,
        String name,
        LocalDate dateOfBirth,
        AccountResponseDto account,
        List<PhoneDataDto> phones,
        List<EmailDataDto> emails
) {
}
