package ru.lkorasik.balance.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.lkorasik.balance.data.entity.User;
import ru.lkorasik.balance.service.MoneyService;

@RestController
@RequestMapping("/api/transfer")
public class TransferController {
    private final MoneyService moneyService;

    @Autowired
    public TransferController(MoneyService moneyService) {
        this.moneyService = moneyService;
    }

    @PostMapping
    public void transfer(@RequestBody TransferRequestDto dto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        moneyService.transferMoney(user, dto.receiverId(), dto.value());
    }
}
