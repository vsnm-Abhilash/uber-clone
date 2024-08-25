package com.abhilash.project.uber.uberApp.strategies;

import com.abhilash.project.uber.uberApp.entities.enums.PaymentMethod;
import com.abhilash.project.uber.uberApp.strategies.Impl.CashPaymentStrategy;
import com.abhilash.project.uber.uberApp.strategies.Impl.WalletPaymentStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentStrategyManager {
    private final CashPaymentStrategy cashPaymentStrategy;
    private final WalletPaymentStrategy walletPaymentStrategy;

    public PaymentStrategy getPaymentStrategy(PaymentMethod paymentMethod){
        if(paymentMethod.equals(PaymentMethod.WALLET)){
            return walletPaymentStrategy;
        }
        else{
            return cashPaymentStrategy;
        }
    }
}
