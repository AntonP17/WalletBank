package com.example.wallet.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "wallet")
public class Wallet {

    @Id
    @Column(name = "wallet_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID walletId;
    @Column(name = "balance")
    private double balance;

    public Wallet() {
    }

    public UUID getWalletId() {
        return walletId;
    }

    public void setWalletId(UUID walletId) {
        this.walletId = walletId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
