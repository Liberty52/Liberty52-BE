package com.liberty52.common.exception.external.unauthorized;

import com.liberty52.common.exception.external.AbstractApiException;
import com.liberty52.common.exception.external.ErrorCodeImpl;

public class UnauthorizedException extends AbstractApiException {
    public UnauthorizedException(String msg) {
        super(ErrorCodeImpl.UNAUTHORIZED, msg);
    }
}
