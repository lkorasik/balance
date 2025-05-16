package ru.lkorasik.balance.api.user;

import java.util.List;

public record AddEmailRequestDto(
        List<String> emails
) {
}
