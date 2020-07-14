package com.adison.shop.common.retry;

import lombok.Setter;
import lombok.extern.java.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Log
@Aspect
public class MethodExecutor {

    @Setter
    private int attempts;

    @Pointcut("@annotation(Retry)")
    public void applyRetryAspect() {
    }

    @Around("applyRetryAspect()")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        int currentAttempt = 0;
        Throwable throwable;
        do {
            try {
                return joinPoint.proceed();
            } catch (Throwable t) {
                throwable = t;
            }
            ++currentAttempt;
            log.info(String.format("%s execution attempts undertaken %d: ", joinPoint.getSignature().getName(), currentAttempt));
        } while (currentAttempt < attempts);
        throw throwable;
    }
}
