package com.adison.shop.common.validator;

import lombok.RequiredArgsConstructor;

import javax.persistence.PersistenceContext;
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
        var violations = validator.validate(object);
        if (!violations.isEmpty()) {
            try {
                Constructor<E> exception = exceptionType.getDeclaredConstructor();
                throw exception.newInstance();
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                //this supposedly handles the exceptions above, which might occur when trying to create our exception instance
                //cause maybe were not passed a type that extends exception at all. maybe that'd have to be validated.
                //or maybe it's not worth it and if you don't get a neat exception type in the validate annotation,
                // you yourself throw an IAE
                throw new IllegalArgumentException();
            }
        }
    }
}
