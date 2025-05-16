package ru.lkorasik.balance.exceptions;

public class UserNotFoundByEmailException extends UserNotFoundException {
    public UserNotFoundByEmailException(String email) {
        super("User with email " + email + " not found");
    }
}
