package com.adison.shop.payments;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import java.time.Instant;
@Log
@RequiredArgsConstructor
public class FakePaymentService implements PaymentService {

    private final PaymentIdGenerator paymentIdGenerator;
    private final PaymentRepository paymentRepository;

    @LogPayments
    @Override
    public Payment process(PaymentRequest paymentRequest) {
        var payment =  Payment.builder()
                .id(paymentIdGenerator.getNext())
                .money(paymentRequest.getMoney())
                .timestamp(Instant.now())
                .status(PaymentStatus.STARTED)
                .build();
        return paymentRepository.save(payment);
    }

    public void init() {
        log.info("Payment service initialized");
    }

    public void destroy() {
        log.info("Payment service going down");
    }
}

