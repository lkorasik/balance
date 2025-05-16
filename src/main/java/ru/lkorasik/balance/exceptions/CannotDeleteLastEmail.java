package ru.lkorasik.balance.exceptions;

public class CannotDeleteLastEmail extends ApplicationException {
    public CannotDeleteLastEmail() {
        super("You cannot delete last user email");
    }
}
