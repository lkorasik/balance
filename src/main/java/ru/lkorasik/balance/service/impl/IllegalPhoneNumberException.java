package ru.lkorasik.balance.service.impl;

import ru.lkorasik.balance.exceptions.ApplicationException;

public class IllegalPhoneNumberException extends ApplicationException {
    public IllegalPhoneNumberException() {
        super("Incorrect phone number");
    }
}
