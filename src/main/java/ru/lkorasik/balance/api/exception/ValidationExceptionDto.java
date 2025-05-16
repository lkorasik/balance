package ru.lkorasik.balance.api.exception;

import java.util.Map;

public record ValidationExceptionDto(
        Map<String, String> errors
) {
}
