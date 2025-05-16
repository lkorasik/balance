package ru.lkorasik.balance.exceptions;

public class UserNotFoundException extends ApplicationException {
    public UserNotFoundException(String email) {
        super("User with email " + email + " not found");
    }
}
