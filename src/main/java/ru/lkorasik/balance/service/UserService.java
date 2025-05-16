package ru.lkorasik.balance.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.lkorasik.balance.api.user.UpdateEmailRequestDto;
import ru.lkorasik.balance.api.user.UpdatePhoneRequestDto;
import ru.lkorasik.balance.data.entity.EmailData;
import ru.lkorasik.balance.data.entity.PhoneData;
import ru.lkorasik.balance.data.entity.User;
import ru.lkorasik.balance.data.repository.UserRepository;
import ru.lkorasik.balance.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    public void addEmail(User user, List<String> emails) {
        emails.forEach(email -> {
            EmailData emailData = new EmailData(email);
            user.addEmail(emailData);
        });
        userRepository.save(user);
    }

    public void updateEmails(User user, List<UpdateEmailRequestDto> emails) {
        Map<Long, EmailData> userEmails = user.getEmails()
                .stream()
                .collect(Collectors.toMap(EmailData::getId, x -> x));

        emails.forEach(email -> {
            if (userEmails.containsKey(email.id())) {
                EmailData emailData = userEmails.get(email.id());
                emailData.setEmail(email.email());
            }
        });

        userRepository.save(user);
    }

    public void addPhone(User user, List<String> phones) {
        phones.forEach(phone -> {
            PhoneData phoneData = new PhoneData(phone);
            user.addPhone(phoneData);
        });
        userRepository.save(user);
    }

    public void updatePhones(User user, List<UpdatePhoneRequestDto> phones) {
        Map<Long, PhoneData> userPhones = user.getPhones()
                .stream()
                .collect(Collectors.toMap(PhoneData::getId, x -> x));

        phones.forEach(phone -> {
            if (userPhones.containsKey(phone.id())) {
                PhoneData phoneData = userPhones.get(phone.id());
                phoneData.setPhone(phone.phone());
            }
        });

        userRepository.save(user);
    }

    @Transactional
    public void deleteEmails(User user, List<Long> emailsIds) {
        if (user.getEmails().size() == 1) {
            throw new CannotDeleteLastEmail();
        }

        List<EmailData> list = user.getEmails().stream().filter(email -> emailsIds.contains(email.getId())).toList();
        list.forEach(user::deleteEmail);
        userRepository.save(user);
    }

    @Transactional
    public void deletePhones(User user, List<Long> phoneIds) {
        if (user.getPhones().size() == 1) {
            throw new CannotDeleteLastPhone();
        }

        List<PhoneData> list = user.getPhones().stream().filter(phone -> phoneIds.contains(phone.getId())).toList();
        list.forEach(user::deletePhone);
        userRepository.save(user);
    }
}
