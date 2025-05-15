package ru.lkorasik.balance.api;

import java.math.BigDecimal;

public record TransferRequestDto(
        long receiverId,
        BigDecimal value
) {
}
