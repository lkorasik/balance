package ru.lkorasik.balance.exceptions;

public class IncorrectTransactionException extends RuntimeException {
    public IncorrectTransactionException(String message) {
        super(message);
    }
}
