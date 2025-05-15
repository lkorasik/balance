package ru.lkorasik.balance.api;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transfer")
public class TransferController {
    @PostMapping
    public void transfer(@RequestBody TransferRequestDto dto) {
        throw new RuntimeException("Not implemented");
    }
}
