package ru.ashabalin.account.service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ashabalin.account.service.services.AccountService;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Long> getAmount(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(accountService.getAmount(id));
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> addAmount(@PathVariable("id") Integer id,
                                          @RequestParam("value") Long value) {
        accountService.addAmount(id, value);
        return ResponseEntity.ok().build();
    }

}
