package com.example.wallet.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import com.example.wallet.model.Wallet;
import com.example.wallet.model.WalletOperation;
import com.example.wallet.service.WalletService;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//@WebMvcTest(WalletController.class)
public class WalletControllerTest {


    private MockMvc mockMvc;

    @Mock
    private WalletService walletService;

    @InjectMocks
    private WalletController walletController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(walletController).build();
    }

    @Test
    void testPerformOperation() throws Exception {
        WalletOperation operation = new WalletOperation();
        operation.setWalletId(UUID.randomUUID());
        operation.setOperationType("DEPOSIT");
        operation.setAmount(100.0);

        when(walletService.performOperation(any(WalletOperation.class))).thenReturn("Operation performed successfully");

        mockMvc.perform(post("/api/v1/wallets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"walletId\":\"" + operation.getWalletId() + "\",\"operationType\":\"DEPOSIT\",\"amount\":100.0}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Operation performed successfully"));
    }

    @Test
    void testGetWallet() throws Exception {
        UUID walletId = UUID.randomUUID();
        Wallet wallet = new Wallet();
        wallet.setWalletId(walletId);
        wallet.setBalance(200.0);

        when(walletService.getWallet(walletId)).thenReturn(wallet);

        mockMvc.perform(get("/api/v1/wallets/" + walletId))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"walletId\":\"" + walletId + "\",\"balance\":200.0}"));
    }

    @Test
    void testPerformOperationWithInvalidJson() throws Exception{
        mockMvc.perform(post("/api/v1/wallets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"invalidField\":\"value\"}"))
                .andExpect(status().isBadRequest());

    }

    @Test
    void testGetWalletNotFound() throws Exception {
        UUID walletId = UUID.randomUUID();

        when(walletService.getWallet(walletId))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Кошелек не найден"));

        mockMvc.perform(get("/api/v1/wallets/" + walletId))
                .andExpect(status().isNotFound());
    }

}
