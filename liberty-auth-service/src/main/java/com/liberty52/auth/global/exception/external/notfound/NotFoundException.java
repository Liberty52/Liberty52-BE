package com.liberty52.auth.global.exception.external.notfound;

import com.liberty52.auth.global.exception.external.AbstractApiException;
import com.liberty52.auth.global.exception.external.AuthErrorCode;

public class NotFoundException extends AbstractApiException {
    public NotFoundException(String msg) {
        super(AuthErrorCode.NOT_FOUND, msg);
    }
}
