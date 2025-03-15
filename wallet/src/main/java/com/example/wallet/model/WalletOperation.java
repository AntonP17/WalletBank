package com.example.wallet.model;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public class WalletOperation {
    @NotNull(message = "walletId is required")
    private UUID walletId;

    @NotNull(message = "operationType is required")
    private String operationType;

    @Positive(message = "amount must be positive")
    private double amount;

    public UUID getWalletId() {
        return walletId;
    }

    public void setWalletId(UUID walletId) {
        this.walletId = walletId;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
