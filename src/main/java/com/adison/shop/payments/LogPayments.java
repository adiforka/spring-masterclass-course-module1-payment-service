package com.adison.shop.payments;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//meta-annotations (we can use this anno only on method level, and it'll be available at runtime
//cause the compiler will not have removed it at compilation
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogPayments {

}
