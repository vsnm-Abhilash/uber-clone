package com.abhilash.project.uber.uberApp.services.impl;

import com.abhilash.project.uber.uberApp.entities.Ride;
import com.abhilash.project.uber.uberApp.entities.User;
import com.abhilash.project.uber.uberApp.entities.Wallet;
import com.abhilash.project.uber.uberApp.entities.WalletTransactions;
import com.abhilash.project.uber.uberApp.entities.enums.TransactionMethod;
import com.abhilash.project.uber.uberApp.entities.enums.TransactionType;
import com.abhilash.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.abhilash.project.uber.uberApp.repositories.WalletRepository;
import com.abhilash.project.uber.uberApp.services.WalletService;
import com.abhilash.project.uber.uberApp.services.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final ModelMapper mapper;
    private final WalletTransactionService walletTransactionService;
    @Override
    @Transactional
    public Wallet addMoneyToWallet(User user, Double amount, String transactionId, Ride ride, TransactionMethod transactionMethod) {
        Wallet wallet=findByUser(user);
        wallet.setBalance(wallet.getBalance()+amount);
        WalletTransactions walletTransactions=WalletTransactions.builder()
                .transactionId(transactionId)
                .transactionMethod(transactionMethod)
                .transactionType(TransactionType.CREDIT)
                .wallet(wallet)
                .ride(ride)
                .amount(amount)
                .build();
       // walletTransactionService.createNewWalletTransaction(walletTransactions);
        wallet.getTransactions().add(walletTransactions);
        return walletRepository.save(wallet);
    }

    @Override
    public void withdrawAllMyMoneyFromWallet() {

    }

    @Override
    public Wallet findWalletById(Long walletId) {
        return walletRepository.findById(walletId)
                .orElseThrow(()->new ResourceNotFoundException("Wallet not found with id "+walletId));
    }

    @Override
    public Wallet createNewWallet(User user) {
        Wallet wallet=new Wallet();
        wallet.setUser(user);
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet findByUser(User user) {
        return walletRepository.findByUser(user)
                .orElseThrow(()->new ResourceNotFoundException("Wallet not found for user with id "+user.getId()));    }

    @Override
    @Transactional
    public Wallet deductMoneyFromWallet(User user, Double amount, String transactionId, Ride ride, TransactionMethod transactionMethod) {
        Wallet wallet=findByUser(user);
        wallet.setBalance(wallet.getBalance()-amount);
        WalletTransactions walletTransactions=WalletTransactions.builder()
                .transactionId(transactionId)
                .transactionMethod(transactionMethod)
                .transactionType(TransactionType.DEBIT)
                .wallet(wallet)
                .ride(ride)
                .amount(amount)
                .build();

        wallet.getTransactions().add(walletTransactions);
        //walletTransactionService.createNewWalletTransaction(walletTransactions);
        return walletRepository.save(wallet);
    }
}
