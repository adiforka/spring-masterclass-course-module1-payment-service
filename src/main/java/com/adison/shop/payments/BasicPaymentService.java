package com.adison.shop.payments;

import com.adison.shop.common.profiler.ExecutionTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;

import javax.transaction.Transactional;
import java.time.Instant;

@Log
@Transactional
@RequiredArgsConstructor
public class BasicPaymentService implements PaymentService{

    private final PaymentIdGenerator paymentIdGenerator;
    private final PaymentRepository paymentRepository;

    @ExecutionTime
    @LogPayments
    public Payment process(PaymentRequest paymentRequest)  {
        var payment = Payment.builder()
                .id(paymentIdGenerator.getNext())
                .money(paymentRequest.getMoney())
                .timestamp(Instant.now())
                .status(PaymentStatus.STARTED)
                .build();
        return paymentRepository.save(payment);
    }
}