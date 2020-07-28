package com.adison.shop.common.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<Name, String> {

    private int minLen;

    //lets us read back from the properties on the related annotation (Name)
    @Override
    public void initialize(Name constraintAnnotation) {
        minLen = constraintAnnotation.minLen();

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext ctx) {
        return s.length() >= minLen;
    }
}
