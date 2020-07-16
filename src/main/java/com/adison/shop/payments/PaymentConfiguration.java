package com.adison.shop.payments;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

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
    public PaymentService fakePaymentService(PaymentIdGenerator uuidPaymentIdGenerator,
                                             PaymentRepository paymentRepository,
                                             ApplicationEventPublisher eventPublisher) {
        return new FakePaymentService(uuidPaymentIdGenerator, paymentRepository, eventPublisher);
    }

    @Bean
    public PaymentConsoleLogger paymentConsoleLogger(MessageSource messageSource) {
        return new PaymentConsoleLogger(messageSource);
    }
}