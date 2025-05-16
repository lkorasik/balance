package ru.lkorasik.balance.api.user;

import java.util.List;

public record UpdateEmailsListRequestDto(
        List<UpdateEmailRequestDto> emails
) {
}
