package ru.lkorasik.balance.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import java.math.BigDecimal;

@Entity
public class Account {
    @Id
    Long id;
    @OneToOne
    User user;
    BigDecimal balance;
}
