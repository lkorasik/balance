package ru.lkorasik.balance.service;

import ru.lkorasik.balance.exceptions.ApplicationException;

public class CannotDeleteLastPhone extends ApplicationException {
    public CannotDeleteLastPhone() {
        super("You cannot delete last user phone");
    }
}
