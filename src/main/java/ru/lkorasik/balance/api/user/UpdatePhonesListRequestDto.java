package ru.lkorasik.balance.api.user;

import java.util.List;

public record UpdatePhonesListRequestDto(
        List<UpdatePhoneRequestDto> phones
){}
