package ru.lkorasik.balance.api;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @PostMapping("/{id}/email")
    public void addEmail(@PathVariable("id") long id, @RequestBody AddEmailRequestDto dto) {
        throw new RuntimeException("Not imeplemented");
    }

    @PostMapping("/{id}/phone")
    public void addPhone(@PathVariable("id") long id, @RequestBody AddPhoneRequestDto dto) {
        throw new RuntimeException("Not implemented");
    }

    @GetMapping("/{id}")
    public void getUser(@PathVariable("id") long id) {
        throw new RuntimeException("Not implemented");
    }

    @GetMapping("/all")
    public void getAllUsers(@RequestBody GetAllUsersRequestDto dto) {
        throw new RuntimeException("Not implemented");
    }
}
