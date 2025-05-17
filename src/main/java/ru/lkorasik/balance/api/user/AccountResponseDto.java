package ru.lkorasik.balance.api.user;

import java.io.Serializable;
import java.math.BigDecimal;

public record AccountResponseDto(
        long id,
        BigDecimal balance
) implements Serializable {
}
