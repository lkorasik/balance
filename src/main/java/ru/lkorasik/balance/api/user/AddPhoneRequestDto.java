package ru.lkorasik.balance.api.user;

import java.util.List;

public record AddPhoneRequestDto(
        List<String> phones
) {
}
