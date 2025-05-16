package ru.lkorasik.balance.service;

import ru.lkorasik.balance.exceptions.ApplicationException;

public class CannotDeleteLastEmail extends ApplicationException {
    public CannotDeleteLastEmail() {
        super("You cannot delete last user email");
    }
}
