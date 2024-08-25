package com.abhilash.project.uber.uberApp.services.impl;

import com.abhilash.project.uber.uberApp.entities.WalletTransactions;
import com.abhilash.project.uber.uberApp.repositories.WalletTransactionRepository;
import com.abhilash.project.uber.uberApp.services.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletTransactionServiceImpl implements WalletTransactionService {

    private final WalletTransactionRepository walletTransactionRepository;
    private final ModelMapper mapper;
    @Override
    public void createNewWalletTransaction(WalletTransactions walletTransaction) {
        walletTransactionRepository.save(walletTransaction);
    }
}
