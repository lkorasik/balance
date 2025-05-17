package ru.lkorasik.balance.service;

import org.springframework.security.core.userdetails.UserDetails;
import ru.lkorasik.balance.api.user.SearchUserRequestDto;
import ru.lkorasik.balance.api.user.UpdateEmailRequestDto;
import ru.lkorasik.balance.api.user.UpdatePhoneRequestDto;
import ru.lkorasik.balance.data.entity.User;

import java.util.List;

public interface UserService {
    void addEmail(User user, List<String> emails);

    void updateEmails(User user, List<UpdateEmailRequestDto> emails);

    void addPhone(User user, List<String> phones);

    void updatePhones(User user, List<UpdatePhoneRequestDto> phones);

    void deleteEmails(User user, List<Long> emailsIds);

    void deletePhones(User user, List<Long> phoneIds);

    List<User> searchUsers(SearchUserRequestDto dto);

    User findById(long id);

    UserDetails loadByEmail(String email);

    UserDetails loadByPhone(String phone);
}
