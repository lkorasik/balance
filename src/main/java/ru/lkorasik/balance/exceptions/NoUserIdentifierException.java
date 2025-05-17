package ru.lkorasik.balance.exceptions;

public class NoUserIdentifierException extends ApplicationException {
    public NoUserIdentifierException() {
        super("You should use email or phone for user identification");
    }
}
