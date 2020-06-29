package com.adison.shop.payments;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

//switches AspectJ into the Spring configuration
@EnableAspectJAutoProxy
@Configuration
public class PaymentsConfiguration {

    //remember scoping for when you have some tricky logic in the bean factory methods
    //with this name (identical with the element injected into the fake service, we don't have to use qualifier)
    @Bean
    public PaymentIdGenerator uuidPaymentIdGenerator() {
        return new UUIDPaymentIdGenerator();
    }

    @Bean
    public PaymentIdGenerator incrementalPaymentIdGenerator() {
        return new IncrementalPaymentIdGenerator();
    }

    @Bean
    public PaymentRepository mapPaymentRepository() {
        return new MapPaymentRepository();
    }

    @Bean(initMethod = "init", destroyMethod = "destroy")
    public PaymentService fakePaymentService(@Qualifier("incrementalPaymentIdGenerator")PaymentIdGenerator paymentIdGenerator,
                                             PaymentRepository paymentRepository) {
        return new FakePaymentService(paymentIdGenerator, paymentRepository);
    }

    @Bean
    public PaymentConsoleLogger paymentConsoleLogger() {
        return new PaymentConsoleLogger();
    }
}
