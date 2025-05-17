package ru.lkorasik.balance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.lkorasik.balance.data.entity.Account;
import ru.lkorasik.balance.data.entity.User;
import ru.lkorasik.balance.data.repository.UserRepository;
import ru.lkorasik.balance.exceptions.NegativeTransactionAmountException;
import ru.lkorasik.balance.exceptions.NotEnoughMoneyOnCardException;
import ru.lkorasik.balance.exceptions.SameUserTransactionException;
import ru.lkorasik.balance.exceptions.ZeroTransactionAmountException;
import ru.lkorasik.balance.service.impl.MoneyServiceImpl;
import ru.lkorasik.balance.service.impl.UserServiceImpl;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class TransferMoneyTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserServiceImpl userService;
    @InjectMocks
    private MoneyServiceImpl moneyService;

    @Test
    public void correctTransaction() {
        Account fromAccount = new Account(1L, BigDecimal.valueOf(10.00));
        User from = new User(fromAccount);
        long userId = 2L;
        Account toAccount = new Account(2L, BigDecimal.valueOf(10.00));
        User to = new User(userId, toAccount);

        Mockito.doReturn(to).when(userService).findById(userId);

        moneyService.transferMoney(from, userId, BigDecimal.valueOf(1.00));

        Assertions.assertEquals(9.00, fromAccount.getBalance().doubleValue(), 0.001);
        Assertions.assertEquals(11.00, toAccount.getBalance().doubleValue(), 0.001);
    }

    @Test
    public void sameAccount() {
        Account account = new Account(1L, BigDecimal.valueOf(10.00));
        long userId = 1L;
        User user = new User(userId, account);

        Mockito.doReturn(user).when(userService).findById(userId);

        Assertions.assertThrows(SameUserTransactionException.class, () -> moneyService.transferMoney(user, userId, BigDecimal.valueOf(1.00)));

        Assertions.assertEquals(10.00, account.getBalance().doubleValue(), 0.001);
    }

    @Test
    public void negativeValue() {
        Account fromAccount = new Account(1L, BigDecimal.valueOf(10.00));
        User from = new User(fromAccount);
        long userId = 2L;
        Account toAccount = new Account(2L, BigDecimal.valueOf(10.00));
        User to = new User(userId, toAccount);

        Mockito.doReturn(to).when(userService).findById(userId);

        Assertions.assertThrows(NegativeTransactionAmountException.class, () -> moneyService.transferMoney(from, userId, BigDecimal.valueOf(-1.00)));

        Assertions.assertEquals(10.00, fromAccount.getBalance().doubleValue(), 0.001);
        Assertions.assertEquals(10.00, toAccount.getBalance().doubleValue(), 0.001);
    }

    @Test
    public void zeroValue() {
        Account fromAccount = new Account(1L, BigDecimal.valueOf(10.00));
        User from = new User(fromAccount);
        long userId = 2L;
        Account toAccount = new Account(2L, BigDecimal.valueOf(10.00));
        User to = new User(userId, toAccount);

        Mockito.doReturn(to).when(userService).findById(userId);

        Assertions.assertThrows(ZeroTransactionAmountException.class, () -> moneyService.transferMoney(from, userId, BigDecimal.ZERO));

        Assertions.assertEquals(10.00, fromAccount.getBalance().doubleValue(), 0.001);
        Assertions.assertEquals(10.00, toAccount.getBalance().doubleValue(), 0.001);
    }

    @Test
    public void notEnoughMoney() {
        Account fromAccount = new Account(1L, BigDecimal.valueOf(10.00));
        User from = new User(fromAccount);
        long userId = 2L;
        Account toAccount = new Account(2L, BigDecimal.valueOf(10.00));
        User to = new User(userId, toAccount);

        Mockito.doReturn(to).when(userService).findById(userId);

        Assertions.assertThrows(NotEnoughMoneyOnCardException.class, () -> moneyService.transferMoney(from, userId, BigDecimal.valueOf(1000.00)));

        Assertions.assertEquals(10.00, fromAccount.getBalance().doubleValue(), 0.001);
        Assertions.assertEquals(10.00, toAccount.getBalance().doubleValue(), 0.001);
    }
}
