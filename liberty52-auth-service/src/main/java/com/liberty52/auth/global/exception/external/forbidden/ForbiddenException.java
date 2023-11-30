package com.liberty52.auth.global.exception.external.forbidden;

import com.liberty52.auth.global.exception.external.AbstractApiException;
import com.liberty52.auth.global.exception.external.AuthErrorCode;

public class ForbiddenException extends AbstractApiException {
    public ForbiddenException(String msg) {
        super(AuthErrorCode.FORBIDDEN, msg);
    }
}
