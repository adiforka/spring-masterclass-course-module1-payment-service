package com.adison.shop.common.validator;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;

@Aspect
@RequiredArgsConstructor
public class ModelValidator {

    private final ValidatorService validatorService;

    //return type: any, method name: any, but arguments must be annotated with validate, whatever type they may be
    //check this: without a space after "@Validate" it gives us a naming error
    @Before("execution(* *(@Validate (*)))")
    public void validate(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        IntStream.range(0, args.length)
                .forEach(index -> validate(methodSignature, args[index], index));
    }

    private void validate(MethodSignature methodSignature, Object argument, int argIndex) {
        Annotation[] annotations = getAnnotations(methodSignature, argIndex);
        Optional<Validate> validateAnnotation = getValidateAnnotation(annotations);
        validateAnnotation.ifPresent(validate -> validatorService.validate(argument, validate.exception()));
    }

    private Annotation[] getAnnotations(MethodSignature methodSignature, int argIndex) {
        //each argument may be annotated with +1 annotations, so we get an array per argument
        return methodSignature.getMethod().getParameterAnnotations()[argIndex];
    }

    private Optional<Validate> getValidateAnnotation(Annotation[] annotations) {
        return Arrays.stream(annotations)
                .filter(annotation -> annotation instanceof Validate)
                .map(annotation -> (Validate) annotation)
                .findFirst();
    }
}
