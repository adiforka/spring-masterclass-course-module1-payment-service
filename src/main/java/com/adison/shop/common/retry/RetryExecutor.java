package com.adison.shop.common.retry;

import lombok.Setter;
import lombok.extern.java.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Log
@Aspect
public class RetryExecutor {

    @Setter
    private int attempts = 3;

    @Around("@annotation(Retry)")
    public Object execute(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        int attempt = 0;
        Throwable throwable;
        do {
            attempt++;
            log.info(String.format("%s execution attempt %d", proceedingJoinPoint.getSignature().getName(), attempt));
            try {
                return proceedingJoinPoint.proceed();
            } catch (Throwable t) {
                throwable = t;
            }
        } while (attempt < attempts);
        throw throwable;
    }


}
