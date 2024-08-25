package com.abhilash.project.uber.uberApp.dto;

import com.abhilash.project.uber.uberApp.entities.Ride;
import com.abhilash.project.uber.uberApp.entities.Wallet;
import com.abhilash.project.uber.uberApp.entities.enums.TransactionMethod;
import com.abhilash.project.uber.uberApp.entities.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletTransactionDTO {
    private Long id;
    private Double amount;
    private TransactionType transactionType;
    private TransactionMethod transactionMethod;
    private Ride ride;
    private String transactionId;
    private Wallet wallet;
    private LocalDateTime timeStamp;
}
