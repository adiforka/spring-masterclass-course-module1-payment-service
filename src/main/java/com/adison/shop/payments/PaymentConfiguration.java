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
    public PaymentRepository mapPaymentRepository() {
        return new MapPaymentRepository();
    }

    @Bean
    public PaymentRepository hibernatePaymentRepository(SessionFactory sessionFactory) {
        return new HibernatePaymentRepository(sessionFactory);
    }

    @Bean
    public PaymentService fakePaymentService(PaymentIdGenerator paymentIdGenerator,
                                             //matching impl for inj by name of method returning it
                                             PaymentRepository hibernatePaymentRepository,
                                             ApplicationEventPublisher eventPublisher) {
        return new FakePaymentService(paymentIdGenerator, hibernatePaymentRepository, eventPublisher);
    }

    @Bean
    public PaymentConsoleLogger paymentConsoleLogger(MessageSource messageSource) {
        return new PaymentConsoleLogger(messageSource);
    }
}