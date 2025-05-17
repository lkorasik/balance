package ru.lkorasik.balance.service.impl;

import ru.lkorasik.balance.exceptions.ApplicationException;

public class IllegalEmailException extends ApplicationException {
    public IllegalEmailException() {
        super("Incorrect email");
    }
}
