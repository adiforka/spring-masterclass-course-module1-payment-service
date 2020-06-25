package com.adison.shop;

import com.adison.shop.payments.*;
import lombok.extern.java.Log;

@Log
public class Application {
    //this is really configuration code, to be handled by an application context later on
    public static void main(String[] args) {
        var paymentIdGenerator = new IncrementalPaymentIdGenerator();
        var paymentService = new FakePaymentService(paymentIdGenerator);

        var paymentRequest = PaymentRequest.builder()
                .money(LocalMoney.of(1_000))
                .build();
        var payment = paymentService.process(paymentRequest);
        log.info(payment.toString());
    }
}
