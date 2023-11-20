package com.liberty52.main.global.exception.external.badrequest;

import com.liberty52.main.global.exception.external.AbstractApiException;
import com.liberty52.main.global.exception.external.ProductErrorCode;

public class BadRequestException extends AbstractApiException {
    public BadRequestException(String msg) {
        super(ProductErrorCode.BAD_REQUEST, msg);
    }
}
