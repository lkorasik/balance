package ru.lkorasik.balance.api.transfer;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransferRequestDto(
        @NotNull
        Long receiverId,
        @NotNull
        @Positive
        BigDecimal value
) {
}
