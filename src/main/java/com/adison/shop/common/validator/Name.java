package com.adison.shop.common.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NameValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Name {

    String message() default "Invalid name";

    //required
    Class<?>[] groups() default {};

    //required
    Class<? extends Payload>[] payload() default {};

    //custom
    int minLen() default 3;
}
