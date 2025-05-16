package ru.lkorasik.balance.service;

import ru.lkorasik.balance.data.entity.User;

import java.math.BigDecimal;

public interface MoneyService {
    void transferMoney(User sourceUser, long targetId, BigDecimal amount);
}
