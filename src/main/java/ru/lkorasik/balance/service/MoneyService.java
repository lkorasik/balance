package ru.lkorasik.balance.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.lkorasik.balance.data.entity.Account;
import ru.lkorasik.balance.data.entity.User;
import ru.lkorasik.balance.data.repository.UserRepository;
import ru.lkorasik.balance.exceptions.NegativeTransactionAmountException;
import ru.lkorasik.balance.exceptions.NotEnoughMoneyOnCardException;
import ru.lkorasik.balance.exceptions.SameUserTransactionException;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class MoneyService {
    private static final Logger log = LoggerFactory.getLogger(MoneyService.class);
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public MoneyService(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Transactional
    public void transferMoney(User sourceUser, long targetId, BigDecimal amount) {
        log.info("Requested transaction from: {}, to: {}, amount: {}", sourceUser.getId(), targetId, amount);

        User targetUser = userService.findById(targetId);

        Account source = sourceUser.getAccount();
        Account target = targetUser.getAccount();

        checkUsers(source, target, amount);

        BigDecimal newBalance = source.getBalance().subtract(amount);
        source.setBalance(newBalance);
        newBalance = target.getBalance().add(amount);
        target.setBalance(newBalance);

        userRepository.save(sourceUser);
        userRepository.save(targetUser);

        log.info("Requested transaction was successful");
    }

    private void checkUsers(Account from, Account to, BigDecimal amount) {
        if (Objects.equals(from.getId(), to.getId())) {
            throw new SameUserTransactionException(from.getId());
        }
        if (amount.signum() == -1) {
            throw new NegativeTransactionAmountException();
        }
        if (from.getBalance().compareTo(amount) < 0) {
            throw new NotEnoughMoneyOnCardException(from.getId());
        }
    }

    @Scheduled(fixedDelay = 30, initialDelay = 30, timeUnit = TimeUnit.SECONDS)
    @Transactional
    public void updateBalance() {
        userRepository.updateBalance();
    }
}
