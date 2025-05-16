package ru.lkorasik.balance.api.user;

public record PageRequestDto(
        int size,
        int number
){}
