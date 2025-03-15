package com.example.wallet.service;

import com.example.wallet.model.Wallet;
import com.example.wallet.model.WalletOperation;
import com.example.wallet.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
//@Transactional
public class WalletService {

    private final WalletRepository walletRepository;

    @Autowired
    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }


    @Transactional(isolation = Isolation.READ_COMMITTED)
    public String performOperation(WalletOperation operation) {
//        Wallet wallet = walletRepository.findById(operation.getWalletId())
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "кошелек не найден"));

        // Поиск кошелька по walletId
        Wallet wallet = walletRepository.findById(operation.getWalletId())
                .orElseGet(() -> {
                    // Если кошелек не найден, создаем новый
                    Wallet newWallet = new Wallet();
                    newWallet.setWalletId(operation.getWalletId());
                    newWallet.setBalance(0.0);
                    return walletRepository.save(newWallet);
                });

        if (operation.getOperationType().equals("DEPOSIT")) {
            wallet.setBalance(wallet.getBalance() + operation.getAmount());
        } else if (operation.getOperationType().equals("WITHDRAW")) {
            if (wallet.getBalance() >= operation.getAmount()) {
                wallet.setBalance(wallet.getBalance() - operation.getAmount());
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "недостаток средств");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "невалидная операция");
        }

        walletRepository.save(wallet);
        return "Операция выполнена успешно. Текущий баланс: " + wallet.getBalance();
    }

    public Wallet getWallet(UUID walletId) {
        return walletRepository.findById(walletId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "кошель не найден:("));
    }
}
