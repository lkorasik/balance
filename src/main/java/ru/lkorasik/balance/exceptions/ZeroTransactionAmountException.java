package ru.lkorasik.balance.exceptions;

public class ZeroTransactionAmountException extends IncorrectTransactionException {
    public ZeroTransactionAmountException() {
        super("You can't transfer a zero amount of money");
    }
}
