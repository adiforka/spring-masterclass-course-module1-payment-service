package com.adison.shop.payments;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.MessageSource;

import java.util.Locale;

@Aspect
//every aspect needs to be a Spring bean
@Log
@RequiredArgsConstructor
public class PaymentConsoleLogger {

    private static final String MESSAGE_KEY = "paymentInfo";

    private final MessageSource messageSource;

    //the method that'll take care of logging
    //(chose annotation-based mechanism to annotate the method this runs after)
    @AfterReturning(value = "@annotation(LogPayments)", returning = "payment")
    public void log(Payment payment) {
        log.info(createLogEntry(payment));
    }

    private String createLogEntry(Payment payment) {
        return messageSource.getMessage(MESSAGE_KEY, new String[] {payment.getMoney().toString()},
                Locale.getDefault());
    }
}

