package com.liberty52.common.exception.external.badrequest;

import com.liberty52.common.exception.external.AbstractApiException;
import com.liberty52.common.exception.external.ErrorCodeImpl;

public class BadRequestException extends AbstractApiException {
    public BadRequestException(String msg) {
        super(ErrorCodeImpl.BAD_REQUEST, msg);
    }
}
