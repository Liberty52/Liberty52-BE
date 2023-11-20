package com.liberty52.main.global.annotation.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NullableNotNegativeValidator.class)
public @interface NullableNotNegative {
    String message() default "Field must not be negative";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String fieldname();
}
