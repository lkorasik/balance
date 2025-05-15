package ru.lkorasik.balance.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class PhoneData {
    @Id
    Long id;
    String phone;
    @ManyToOne
    User user;
}
