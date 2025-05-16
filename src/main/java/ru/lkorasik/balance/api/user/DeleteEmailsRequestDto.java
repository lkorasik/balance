package ru.lkorasik.balance.api.user;

import java.util.List;

public record DeleteEmailsRequestDto(
        List<Long> emailIds
) {
}
