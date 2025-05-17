package ru.lkorasik.balance.api.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UpdatePhoneRequestDto(
        @NotNull(message = "Id cannot be null")
        Long id,
        @NotNull(message = "Phone cannot be null")
        @NotEmpty(message = "Phone cannot be empty")
        String phone
) {}
