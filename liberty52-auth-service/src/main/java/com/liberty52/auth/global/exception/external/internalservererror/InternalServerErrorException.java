package com.liberty52.auth.global.exception.external.internalservererror;


import com.liberty52.common.exception.external.AbstractApiException;
import com.liberty52.common.exception.external.ErrorCodeImpl;

public class InternalServerErrorException extends AbstractApiException {
    public InternalServerErrorException(String msg) {
        super(ErrorCodeImpl.INTERNAL_SERVER_ERROR, msg);
    }
}
