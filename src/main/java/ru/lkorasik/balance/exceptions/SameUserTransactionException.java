package ru.lkorasik.balance.exceptions;

public class SameUserTransactionException extends IncorrectTransactionException {
    public SameUserTransactionException(Long id) {
        super("Can't transfer money to the same card with id " + id);
    }
}
