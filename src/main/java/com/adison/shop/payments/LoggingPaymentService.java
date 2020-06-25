package com.adison.shop.payments;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
@RequiredArgsConstructor
//this is a proxy class for FakePaymentService
public class LoggingPaymentService implements PaymentService {

    private static final String LOG_ENTRY = "A new payment for %s has been initiated";

    private final FakePaymentService paymentService;

    @Override
    public Payment process(PaymentRequest paymentRequest) {
        //actual processing of PaymentRequest DELEGATED to the instance of FakePaymentService
        var payment = paymentService.process(paymentRequest);
        log.info(createLogEntry(payment));
        return payment;
    }

    private String createLogEntry(Payment payment) {
        return String.format(LOG_ENTRY, payment.getMoney());
    }
}
