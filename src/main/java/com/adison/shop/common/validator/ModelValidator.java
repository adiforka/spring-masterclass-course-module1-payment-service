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

    //return type: any, method name: any, but arguments must be annotated with validate, whatever type they may be
    //check this: without a space after "@Validate" it gives us a naming error
    //execution(* *(@Validate (*)))
    @Before("execution(* *(@Validate (*)))")
    public void validate(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        IntStream.range(0, args.length)
                .forEach(index -> validate(methodSignature, args[index]));
    }

    //we do a lot of work on this universal model validator to make sure we only validate the arguments to a method
    //that have been annotated with out Custom validation annotation
    private void validate(MethodSignature methodSignature, Object argument) {
        Annotation[][] annotations = getAnnotations(methodSignature);
        Optional<Validate> optionalValidateAnno = getValidateAnnotation(annotations);
        optionalValidateAnno.ifPresent(validate -> validatorService.validate(argument, validate.exception()));
    }

    private Annotation[][] getAnnotations(MethodSignature methodSignature) {
        //each argument may be annotated with +1 annotations, so we get an array per argument
        //and a matrix all in all, since there may be more than one argument
        return methodSignature.getMethod().getParameterAnnotations();
    }

    private Optional<Validate> getValidateAnnotation(Annotation[][] annotations) {
        return Arrays.stream(annotations)
                .flatMap(Stream::of)
                .filter(annotation -> annotation instanceof Validate)
                .map(annotation -> (Validate) annotation)
                .findFirst();
    }
}
