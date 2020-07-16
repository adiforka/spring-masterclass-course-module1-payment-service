package com.adison.shop.common.profiler;

import lombok.extern.java.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Order(1000)
@Aspect
@Log
public class Profiler {

    //we're building a universal profiler, so for the return type we're grabbing Object as sth general
    //designator types: annotation, bean, execution (here interested in methods returning a particular type.
    // specifically, for execution designators: full package name of returned type, full package name of the wrapped
    // method, full types of arguments passed to the method. * wildcard or .. wildcard for args)
    @Around("execution(* com.adison.shop.payments.BasicPaymentService.process(..))")
    //@Around("bean(basicPaymentService)")
    //@Around("@annotation(ExecutionTime)")
    //proceeding join point is distinctive of the Around advice type. other types have joinPoints, but not
    //proceeding. this one has a proceed() method, which hands over execution to the wrapped method.
    public Object logExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.nanoTime();
        //may throw an exception cause the wrapped method may throw an exception too (diff signatures,
        //remember we're building a relatively universal profiler. There's no point handling such an exception
        //here, since if one is thrown it makes no sense to time the original method
        Object result = proceedingJoinPoint.proceed();
        long totalTime = System.nanoTime() - startTime;
        log.info(String.format("%s executed in %d ns", proceedingJoinPoint.getSignature(), totalTime));
        return result;
    }
}
