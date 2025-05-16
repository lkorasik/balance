package ru.lkorasik.balance.api.transfer;

import java.math.BigDecimal;

public record TransferRequestDto(
        long receiverId,
        BigDecimal value
) {
}
