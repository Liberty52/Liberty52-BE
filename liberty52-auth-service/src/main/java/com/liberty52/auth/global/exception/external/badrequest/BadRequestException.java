package com.liberty52.auth.global.exception.external.badrequest;

import com.liberty52.auth.global.exception.external.AbstractApiException;
import com.liberty52.auth.global.exception.external.AuthErrorCode;

public class BadRequestException extends AbstractApiException {
    public BadRequestException(String msg) {
        super(AuthErrorCode.BAD_REQUEST, msg);
    }
}
