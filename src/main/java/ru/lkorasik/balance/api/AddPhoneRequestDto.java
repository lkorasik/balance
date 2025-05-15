package ru.lkorasik.balance.api;

import java.util.List;

public record AddPhoneRequestDto(
        List<String> phones
) {
}
