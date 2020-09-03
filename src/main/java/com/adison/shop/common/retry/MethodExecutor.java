package com.adison.shop.common.retry;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Log
@Aspect
@RequiredArgsConstructor
public class MethodExecutor {

    private final int attempts;

    @Around("@annotation(Retry)")
    public Object execute(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        int currentAttempt = 0;
        Throwable throwable;
        do {
            currentAttempt++;
            log.info("Executing method: " + proceedingJoinPoint.getSignature().getName()
                    + ", attempt: " + currentAttempt);
            try {
                return proceedingJoinPoint.proceed();
            } catch (Throwable t) {
               throwable = t;
            }
        } while (currentAttempt < attempts);
        throw throwable;
    }
}
