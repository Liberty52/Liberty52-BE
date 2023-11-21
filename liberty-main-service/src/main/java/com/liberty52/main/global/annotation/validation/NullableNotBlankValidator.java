package com.liberty52.main.global.annotation.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NullableNotBlankValidator implements ConstraintValidator<NullableNotBlank, String> {
    private String fieldName;

    @Override
    public void initialize(NullableNotBlank annotation) {
        fieldName = annotation.fieldname();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //null이거나 빈 문자열이 아닌 경우에만 통과 -> Patch 메소드의 DTO 유효성 검사를 위해 제작(특정 필드의 값을 전송하지 않거나, 전송할거면 유효성 검사를 통과하도록)
        if (value == null || !value.trim().isEmpty()) return true;
        else {
            //커스텀 에러메세지
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Field [" + fieldName + "] must not be blank").addConstraintViolation();
            return false;
        }
    }
}