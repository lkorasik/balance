package ru.lkorasik.balance.exceptions;

public class CannotDeleteLastPhone extends ApplicationException {
    public CannotDeleteLastPhone() {
        super("You cannot delete last user phone");
    }
}
