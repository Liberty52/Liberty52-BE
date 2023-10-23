package com.liberty52.product.global.annotation.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NullableNotBlankValidator.class)
public @interface NullableNotBlank {
    String message() default "Field must not be blank";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String fieldname();
}
