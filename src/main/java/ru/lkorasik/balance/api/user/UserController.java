package ru.lkorasik.balance.api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.lkorasik.balance.data.entity.User;
import ru.lkorasik.balance.exceptions.UnauthorizedUserChangeException;
import ru.lkorasik.balance.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/{id}/email")
    public void addEmail(@PathVariable("id") long id, @RequestBody AddEmailRequestDto dto) {
        User user = getCurrentUser(id);
        userService.addEmail(user, dto.emails());
    }

    @PatchMapping("/{id}/email")
    public void updateEmail(@PathVariable("id") long id, @RequestBody UpdateEmailsListRequestDto dto) {
        User user = getCurrentUser(id);
        userService.updateEmails(user, dto.emails());
    }

    @DeleteMapping("/{id}/email")
    public void deleteEmail(@PathVariable("id") long id, @RequestBody DeleteEmailsRequestDto dto) {
        User user = getCurrentUser(id);
        userService.deleteEmails(user, dto.emailIds());
    }

    @PostMapping("/{id}/phone")
    public void addPhone(@PathVariable("id") long id, @RequestBody AddPhoneRequestDto dto) {
        User user = getCurrentUser(id);
        userService.addPhone(user, dto.phones());
    }

    @PatchMapping("/{id}/phone")
    public void updatePhone(@PathVariable("id") long id, @RequestBody UpdatePhonesListRequestDto dto) {
        User user = getCurrentUser(id);
        userService.updatePhones(user, dto.phones());
    }

    @DeleteMapping("/{id}/phone")
    public void deletePhone(@PathVariable("id") long id, @RequestBody DeletePhonesRequestDto dto) {
        User user = getCurrentUser(id);
        userService.deletePhones(user, dto.phoneIds());
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") long id) {
        return getCurrentUser(id);
    }

    @GetMapping("/all")
    public List<User> searchUsers(@RequestBody SearchUserRequestDto dto) {
        return userService.searchUsers(dto);
    }

    private User getCurrentUser(long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.getId() != id) {
            throw new UnauthorizedUserChangeException(user.getId(), id);
        }
        return user;
    }
}
