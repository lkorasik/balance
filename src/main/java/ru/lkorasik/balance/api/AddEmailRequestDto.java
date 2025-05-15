package ru.lkorasik.balance.api;

import java.util.List;

public record AddEmailRequestDto(
        List<String> emails
) {
}
