package com.example.wallet.controllers;


import com.example.wallet.model.Wallet;
import com.example.wallet.model.WalletOperation;
import com.example.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public String performOperation(@RequestBody WalletOperation operation) {
        return walletService.performOperation(operation);
    }

    @GetMapping("/{walletId}")
    public Wallet getWallet(@PathVariable UUID walletId) {
        return walletService.getWallet(walletId);
    }
}
