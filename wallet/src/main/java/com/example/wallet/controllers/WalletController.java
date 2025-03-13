package com.example.wallet.controllers;


import com.example.wallet.model.Wallet;
import com.example.wallet.model.WalletOperation;
import com.example.wallet.service.WalletService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wallets")
public class WalletController {

    private final WalletService walletService;

    @Autowired
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping
    public String performOperation(@Valid @RequestBody WalletOperation operation, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid JSON");
        }
        return walletService.performOperation(operation);
    }

    @GetMapping("/{walletId}")
    public Wallet getWallet(@PathVariable UUID walletId) {
        return walletService.getWallet(walletId);
    }
}
