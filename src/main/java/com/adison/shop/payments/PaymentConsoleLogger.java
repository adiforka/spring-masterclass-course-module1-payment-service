package com.adison.shop.payments;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.aspectj.lang.annotation.*;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.util.Locale;

//orders the aspects on a particular wrapped method.
//@Order(3)
@Aspect
@Log
@RequiredArgsConstructor
public class PaymentConsoleLogger implements Ordered {

    private static final String MESSAGE_KEY = "paymentInfo";

    private final MessageSource messageSource;

    @Before(value = "@annotation(LogPayments) && args(paymentRequest)")
    public void beforePayment(PaymentRequest paymentRequest) {
        log.info("New payment request: " + paymentRequest);
    }

    //run independently of the outcome of the core metod
    @After(value = "@annotation(LogPayments)")
    public void AfterPayment() {
        log.info("After payment");
    }

    @AfterThrowing(value = "@annotation(LogPayments)", throwing = "exception")
    public void onException(Exception exception) {
        log.info("Payment exception: " + exception.getClass().getSimpleName());
    }

    @AfterReturning(value = "@annotation(LogPayments)", returning = "payment")
    public void log(Payment payment) {
        log.info(createLogEntry(payment));
    }

    private String createLogEntry(Payment payment) {
        return messageSource.getMessage(MESSAGE_KEY, new String[]{payment.getMoney().toString()},
                Locale.getDefault());
    }

    @Override
    public int getOrder() {
        return 3;
    }
}

