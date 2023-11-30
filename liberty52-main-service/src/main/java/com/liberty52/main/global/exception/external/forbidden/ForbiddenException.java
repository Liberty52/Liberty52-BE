package com.liberty52.main.global.exception.external.forbidden;

import com.liberty52.common.exception.external.AbstractApiException;
import com.liberty52.common.exception.external.ErrorCodeImpl;

public class ForbiddenException extends AbstractApiException {
    public ForbiddenException(String msg) {
        super(ErrorCodeImpl.FORBIDDEN, msg);
    }
}
