package com.adison.shop.payments;

import lombok.extern.java.Log;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
//every aspect needs to be a Spring bean
@Component
@Log
public class PaymentConsoleLogger {

    private static final String LOG_ENTRY = "A new payment for %s has been initiated";

    //the method that'll take care of logging
    //(chose annotation-based mechanism to annotate the method this runs after)
    @AfterReturning(value = "@annotation(LogPayments)", returning = "payment")
    public void log(Payment payment) {
        log.info(createLogEntry(payment));
    }

    private String createLogEntry(Payment payment) {
        return String.format(LOG_ENTRY, payment.getMoney());
    }
}

