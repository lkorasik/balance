package ru.lkorasik.balance.exceptions;

public class UserNotFoundByPhoneException extends UserNotFoundException {
    public UserNotFoundByPhoneException(String phone) {
        super("User with phone " + phone + " not found");
    }
}
