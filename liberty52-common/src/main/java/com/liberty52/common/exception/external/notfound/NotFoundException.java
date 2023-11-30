package com.liberty52.common.exception.external.notfound;

import com.liberty52.common.exception.external.AbstractApiException;
import com.liberty52.common.exception.external.ErrorCodeImpl;

public class NotFoundException extends AbstractApiException {
    public NotFoundException(String msg) {
        super(ErrorCodeImpl.RESOURCE_NOT_FOUND, msg);
    }
}
