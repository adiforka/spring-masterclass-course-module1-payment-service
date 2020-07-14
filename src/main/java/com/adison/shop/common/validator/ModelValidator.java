package com.adison.shop.common.validator;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;

@Aspect
@RequiredArgsConstructor
public class ModelValidator {

    private final ValidatorService validatorService;

    @Pointcut("execution(* * (@Validate (*)))")
    public void applyValidationAspect() { }

    @Before("applyValidationAspect()")
    //@Before("execution(* *(@Validate (*)))")
    public void validate(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        IntStream.range(0, args.length)
                .forEach(index -> validate(methodSignature, args[index], index));
    }

    private void validate(MethodSignature methodSignature, Object argument, int index) {
        Annotation[] perArgAnnotations = getPerArgAnnotations(methodSignature, index);
        Optional<Validate> optionalValidateAnno = getThisArgsValidateAnnotations(perArgAnnotations);
        optionalValidateAnno.ifPresent(validate -> validatorService.validate(argument, validate.exception()));
    }

    private Annotation[] getPerArgAnnotations(MethodSignature methodSignature, int argIndex) {
        return methodSignature.getMethod().getParameterAnnotations()[argIndex];
    }

    private Optional<Validate> getThisArgsValidateAnnotations(Annotation[] annotations) {
        //is this particular argument of the method targeted preliminarily for validation annotated with @Validate?
        return Arrays.stream(annotations)
                .filter(annotation -> annotation instanceof Validate)
                .map(annotation -> (Validate) annotation)
                .findFirst();
    }
}
