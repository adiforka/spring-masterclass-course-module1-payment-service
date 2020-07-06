package com.adison.shop.common.validator;

import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

@RequiredArgsConstructor
public class ValidatorService {

    private final Validator validator;

    //generic, since we don't know what type we'll be validating or what exception we might throw
    public <O, E extends RuntimeException> void validate(O object, Class<E> exceptionType) throws E {
        Set<ConstraintViolation<O>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            try {
                Constructor<E> exception = exceptionType.getDeclaredConstructor();
                throw exception.newInstance();
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                //this supposedly handles the exceptions above, which might occur when trying to create our exception instance
                throw new IllegalArgumentException();
            }
        }
    }
}
