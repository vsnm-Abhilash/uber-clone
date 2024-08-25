package com.abhilash.project.uber.uberApp.strategies.Impl;

import com.abhilash.project.uber.uberApp.entities.Driver;
import com.abhilash.project.uber.uberApp.entities.Payment;
import com.abhilash.project.uber.uberApp.entities.Rider;
import com.abhilash.project.uber.uberApp.entities.enums.PaymentStatus;
import com.abhilash.project.uber.uberApp.entities.enums.TransactionMethod;
import com.abhilash.project.uber.uberApp.repositories.PaymentRepository;
import com.abhilash.project.uber.uberApp.services.PaymentService;
import com.abhilash.project.uber.uberApp.services.WalletService;
import com.abhilash.project.uber.uberApp.strategies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

//Rider has 500 in wallet,Driver has 500 in wallet
//Ride cost 100, commission=30
//Rider->500-100=400
//Driver->500+(100-30)70=570

@Service
@RequiredArgsConstructor
public class WalletPaymentStrategy implements PaymentStrategy {
    private final WalletService walletService;
    private final PaymentRepository paymentRepository;

    @Override
    public void processPayment(Payment payment) {
        Driver driver=payment.getRide().getDriver();
        Rider rider=payment.getRide().getRider();
        double driversCut =payment.getAmount()*(1-PLATFORM_COMMISSION);
        walletService.deductMoneyFromWallet(rider.getUser(),payment.getAmount(),null
                ,payment.getRide(), TransactionMethod.RIDE);
        walletService.addMoneyToWallet(driver.getUser(),driversCut,null
                ,payment.getRide(), TransactionMethod.RIDE);

        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);
    }
}
