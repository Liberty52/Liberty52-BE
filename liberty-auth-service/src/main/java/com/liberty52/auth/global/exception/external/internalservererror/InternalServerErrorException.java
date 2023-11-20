package com.liberty52.auth.global.exception.external.internalservererror;

import com.liberty52.auth.global.exception.external.AbstractApiException;
import com.liberty52.auth.global.exception.external.AuthErrorCode;

public class InternalServerErrorException extends AbstractApiException {
    public InternalServerErrorException(String msg) {
        super(AuthErrorCode.INTERNAL_SERVER_ERROR, msg);
    }
}
