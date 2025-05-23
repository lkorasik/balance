package ru.lkorasik.balance.exceptions;

public class UnauthorizedUserChangeException extends ApplicationException {
    public UnauthorizedUserChangeException(long userId, long anotherUserId) {
        super("User with id " + userId + " cannot change user with id " + anotherUserId);
    }
}
