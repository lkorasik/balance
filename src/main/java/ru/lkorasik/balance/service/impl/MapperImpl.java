package ru.lkorasik.balance.service.impl;

import org.springframework.stereotype.Service;
import ru.lkorasik.balance.api.user.AccountResponseDto;
import ru.lkorasik.balance.api.user.EmailDataDto;
import ru.lkorasik.balance.api.user.PhoneDataDto;
import ru.lkorasik.balance.api.user.UserResponseDto;
import ru.lkorasik.balance.data.entity.User;
import ru.lkorasik.balance.service.Mapper;

@Service
public class MapperImpl implements Mapper {
    @Override
    public UserResponseDto map(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getDateOfBirth(),
                new AccountResponseDto(
                        user.getAccount().getId(),
                        user.getAccount().getBalance()
                ),
                user.getPhones().stream().map(x -> new PhoneDataDto(x.getId(), x.getPhone())).toList(),
                user.getEmails().stream().map(x -> new EmailDataDto(x.getId(), x.getEmail())).toList()
        );
    }
}
