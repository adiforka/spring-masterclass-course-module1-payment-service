package com.adison.shop.payments;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConfiguration {

    @Bean
    public PaymentIdGenerator incrementalPaymentIdGenerator() {
        return new IncrementalPaymentIdGenerator();
    }

    @Bean
    public UUIDPaymentIdGenerator uuidPaymentIdGenerator() {
        return new UUIDPaymentIdGenerator();
    }

    //removing all the beans for repositories that implemented PaymentRepository -- Spring Data will generate an
    //implementation and automatically treat it as a bean

    @Bean
    public BasicPaymentService basicPaymentService(PaymentIdGenerator uuidPaymentIdGenerator,
                                             PaymentRepository paymentRepository,
                                             ApplicationEventPublisher eventPublisher) {
        return new BasicPaymentService(uuidPaymentIdGenerator, paymentRepository, eventPublisher);
    }

    @Bean
    public PaymentConsoleLogger paymentConsoleLogger(MessageSource messageSource) {
        return new PaymentConsoleLogger(messageSource);
    }
}