package ru.lkorasik.balance.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class EmailData {
    @Id
    Long id;
    String email;
    @ManyToOne
    User user;
}
