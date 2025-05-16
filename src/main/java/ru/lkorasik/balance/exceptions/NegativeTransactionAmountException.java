package ru.lkorasik.balance.exceptions;

public class NegativeTransactionAmountException extends IncorrectTransactionException {
    public NegativeTransactionAmountException() {
        super("You can't transfer a negative amount of money");
    }
}
