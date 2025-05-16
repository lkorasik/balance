package ru.lkorasik.balance.api.user;

import java.util.List;

public record DeletePhonesRequestDto(
        List<Long> phoneIds
) {
}
