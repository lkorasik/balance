package ru.lkorasik.balance.service;

import ru.lkorasik.balance.api.user.UserResponseDto;
import ru.lkorasik.balance.data.entity.User;

public interface Mapper {
    UserResponseDto map(User user);
}
