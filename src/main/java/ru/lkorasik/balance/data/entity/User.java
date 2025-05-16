package ru.lkorasik.balance.data.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Table(name = "`user`")
@Entity
public class User implements UserDetails {
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return emails.stream().findFirst().get().email;
    }
}
