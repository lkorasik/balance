package ru.lkorasik.balance.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class EmailData {
    @Id
    Long id;
    String email;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
