package com.abhilash.project.uber.uberApp.strategies.Impl;

import com.abhilash.project.uber.uberApp.entities.Driver;
import com.abhilash.project.uber.uberApp.entities.Payment;
import com.abhilash.project.uber.uberApp.entities.enums.PaymentStatus;
import com.abhilash.project.uber.uberApp.entities.enums.TransactionMethod;
import com.abhilash.project.uber.uberApp.repositories.PaymentRepository;
import com.abhilash.project.uber.uberApp.services.WalletService;
import com.abhilash.project.uber.uberApp.strategies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

//Suppose Ride is of 100Rs Rider pays->100
//We deduct 30 as our commission and add 70 to Driver Wallet
@Service
@RequiredArgsConstructor
public class CashPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;
    private final PaymentRepository paymentRepository;
    @Override
    public void processPayment(Payment payment) {
        Driver driver=payment.getRide().getDriver();
        double platformCommission=payment.getAmount()*PLATFORM_COMMISSION;
        walletService.deductMoneyFromWallet(driver.getUser(),platformCommission,null
                ,payment.getRide(), TransactionMethod.RIDE);

        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);

    }
}
