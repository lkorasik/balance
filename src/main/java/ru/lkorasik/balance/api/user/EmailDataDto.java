package ru.lkorasik.balance.api.user;

import java.io.Serializable;

public record EmailDataDto(
        long id,
        String email
) implements Serializable {
}
