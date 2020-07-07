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
import java.util.stream.Stream;

@Aspect
@RequiredArgsConstructor
public class ModelValidator {

    private final ValidatorService validatorService;

    @Before("execution(* *(@Validate (*)))")
    public void validate(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        IntStream.range(0, args.length)
                .forEach(index -> validate(methodSignature, args[index], index));
    }

    private void validate(MethodSignature methodSignature, Object argument, int index) {
        Annotation[] annotations = getAnnotations(methodSignature, index);
        Optional<Validate> optionalValidateAnno = getValidateAnnotation(annotations);
        optionalValidateAnno.ifPresent(validate -> validatorService.validate(argument, validate.exception()));
    }

    private Annotation[] getAnnotations(MethodSignature methodSignature, int argIndex) {
        return methodSignature.getMethod().getParameterAnnotations()[argIndex];
    }

    private Optional<Validate> getValidateAnnotation(Annotation[] annotations) {
        return Arrays.stream(annotations)
                .filter(annotation -> annotation instanceof Validate)
                .map(annotation -> (Validate) annotation)
                .findFirst();
    }
}
