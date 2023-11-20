package com.liberty52.main.global.exception.external.notfound;

import com.liberty52.main.global.exception.external.AbstractApiException;
import com.liberty52.main.global.exception.external.ProductErrorCode;

public class NotFoundException extends AbstractApiException {
    public NotFoundException(String msg) {
        super(ProductErrorCode.RESOURCE_NOT_FOUND, msg);
    }
}
