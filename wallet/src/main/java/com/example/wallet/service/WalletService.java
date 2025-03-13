package com.example.wallet.service;

import com.example.wallet.model.Wallet;
import com.example.wallet.model.WalletOperation;
import com.example.wallet.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    @Autowired
    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public String performOperation(WalletOperation operation) {
        Wallet wallet = walletRepository.findById(operation.getWalletId())
                .orElseThrow(() -> new RuntimeException("кошелек не найден"));

        if (operation.getOperationType().equals("DEPOSIT")) {
            wallet.setBalance(wallet.getBalance() + operation.getAmount());
        } else if (operation.getOperationType().equals("WITHDRAW")) {
            if (wallet.getBalance() >= operation.getAmount()) {
                wallet.setBalance(wallet.getBalance() - operation.getAmount());
            } else {
                return "Недостаточно средств";
            }
        }

        walletRepository.save(wallet);
        return "операция выполнена";
    }

    public Wallet getWallet(UUID walletId) {
        return walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("кошелек не найден"));
    }
}
