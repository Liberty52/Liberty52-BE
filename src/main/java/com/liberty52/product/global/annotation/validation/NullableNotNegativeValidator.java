package com.liberty52.product.global.annotation.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NullableNotNegativeValidator implements ConstraintValidator<NullableNotNegative, Number> {
    private String fieldName;
    @Override
    public void initialize(NullableNotNegative annotation) {
        fieldName = annotation.fieldname();
    }
    @Override
    public boolean isValid(Number value, ConstraintValidatorContext context) {
        //null이거나 0이상일때만 통과
       if(value == null || value.longValue() >= 0) return true;
       else{
           //커스텀 에러메세지
           context.disableDefaultConstraintViolation();
           context.buildConstraintViolationWithTemplate("Field [" + fieldName + "] must not be Negative").addConstraintViolation();
           return false;
       }
    }
}