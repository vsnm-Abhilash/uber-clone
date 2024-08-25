package com.abhilash.project.uber.uberApp.entities;

import com.abhilash.project.uber.uberApp.entities.enums.TransactionMethod;
import com.abhilash.project.uber.uberApp.entities.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(indexes = {
        @Index(name="idx_wallet_transaction_wallet",columnList = "wallet_id"),
        @Index(name="idx_wallet_transaction_ride",columnList = "ride_id")
})
public class WalletTransactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    private TransactionMethod transactionMethod;

    @ManyToOne
    private Ride ride;

    private String transactionId;

    @ManyToOne
    private Wallet wallet;
    @CreationTimestamp
    private LocalDateTime timeStamp;

}
