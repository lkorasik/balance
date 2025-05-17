package ru.lkorasik.balance.api.user;

import java.io.Serializable;

public record PhoneDataDto(
        long id,
        String phone
) implements Serializable {
}
