package ru.lkorasik.balance.api.user;

import java.math.BigDecimal;

public record AccountResponseDto(
        long id,
        BigDecimal balance
) {
}
