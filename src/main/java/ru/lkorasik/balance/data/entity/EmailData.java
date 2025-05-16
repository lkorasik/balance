package ru.lkorasik.balance.data.entity;

import jakarta.persistence.*;

@Entity
public class EmailData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String email;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    public EmailData() {
    }

    public EmailData(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
