package ru.lkorasik.balance.exceptions;

public class NotEnoughMoneyOnCardException extends ApplicationException {
    public NotEnoughMoneyOnCardException(long id) {
        super("On card with id " + id + " not enough money");
    }
}
