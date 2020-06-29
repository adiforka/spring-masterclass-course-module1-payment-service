package com.adison.shop;

import com.adison.shop.payments.*;
import lombok.extern.java.Log;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Log
public class Application {
    //tell the container where to look for configuration for the app
    private static final String BASE_PACKAGE = "com.adison.shop";

    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext applicationContext = new
                AnnotationConfigApplicationContext(BASE_PACKAGE)){
            var paymentService = applicationContext.getBean(PaymentService.class);
            var paymentRequest = PaymentRequest.builder()
                    .money(LocalMoney.of(1_000))
                    .build();
            var payment = paymentService.process(paymentRequest);
            log.info(payment.toString());
        }
    }
}
