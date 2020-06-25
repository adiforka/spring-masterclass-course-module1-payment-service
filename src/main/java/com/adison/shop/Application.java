package com.adison.shop;

import com.adison.shop.payments.FakePaymentService;
import com.adison.shop.payments.LocalMoney;
import com.adison.shop.payments.PaymentRequest;
import lombok.extern.java.Log;

@Log
public class Application {
    public static void main(String[] args) {
        var paymentService = new FakePaymentService();
        var paymentRequest = PaymentRequest.builder()
                .money(LocalMoney.of(1_000))
                .build();
        var payment = paymentService.process(paymentRequest);
        log.info(payment.toString());
    }
}
