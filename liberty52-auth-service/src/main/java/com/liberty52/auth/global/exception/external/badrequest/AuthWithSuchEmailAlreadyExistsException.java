package com.liberty52.auth.global.exception.external.badrequest;

import com.liberty52.common.exception.external.badrequest.BadRequestException;

public class AuthWithSuchEmailAlreadyExistsException extends BadRequestException {
    public AuthWithSuchEmailAlreadyExistsException() {
        super("이미 존재하는 이메일입니다.");
    }
}
