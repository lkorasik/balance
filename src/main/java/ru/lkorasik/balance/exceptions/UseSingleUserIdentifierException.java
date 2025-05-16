package ru.lkorasik.balance.exceptions;

public class UseSingleUserIdentifierException extends ApplicationException {
    public UseSingleUserIdentifierException() {
        super("You should use email or phone for user identification");
    }
}
