package ru.lkorasik.balance.api.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UpdateEmailsListRequestDto(
        @NotNull(message = "List cannot be null")
        @NotEmpty(message = "List cannot be empty")
        List<UpdateEmailRequestDto> emails
) {
}
