package com.adison.shop.payments;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.Instant;

@Log
@Service("paymentService")
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class FakePaymentService implements PaymentService {

    private final PaymentIdGenerator paymentIdGenerator;
    private final PaymentRepository paymentRepository;

    @Autowired
    public FakePaymentService(@IdGenerator (value = "incremental") PaymentIdGenerator paymentIdGenerator,
                              PaymentRepository paymentRepository) {
        this.paymentIdGenerator = paymentIdGenerator;
        this.paymentRepository = paymentRepository;
    }

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

    //bean management methods (no returns, no parameters, no exceptions thrown)
    @PostConstruct
    public void init() {
        log.info("Payment service initialized");
    }
    //works only on singleton (with prototype etc. Spring references a component and then stops managing it)
    //requires that the application context be closed correctly, either manually or with TWR
    @PreDestroy
    public void destroy() {
        log.info("Payment service going down");
    }
}

