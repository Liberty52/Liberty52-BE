package com.liberty52.product.global.exception.external.unauthorized;

import com.liberty52.product.global.exception.external.AbstractApiException;
import com.liberty52.product.global.exception.external.ProductErrorCode;

public class UnauthorizedException extends AbstractApiException {
    public UnauthorizedException(String msg) {
        super(ProductErrorCode.UNAUTHORIZED, msg);
    }
}
