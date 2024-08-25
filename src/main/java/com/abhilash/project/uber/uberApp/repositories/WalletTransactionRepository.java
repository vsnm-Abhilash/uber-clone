package com.abhilash.project.uber.uberApp.repositories;

import com.abhilash.project.uber.uberApp.entities.WalletTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletTransactionRepository extends JpaRepository<WalletTransactions,Long> {
}
