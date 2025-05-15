package ru.lkorasik.balance.data;

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
