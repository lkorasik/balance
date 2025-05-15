package ru.lkorasik.balance.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.time.LocalDate;
import java.util.List;

@Entity
public class User {
    @Id
    Long id;
    String name;
    LocalDate dateOfBirth;
    String password;
    @OneToOne(mappedBy = "user")
    Account account;
    @OneToMany(mappedBy = "user")
    List<PhoneData> phones;
    @OneToMany(mappedBy = "user")
    List<EmailData> emails;
}
