package com.liberty52.product.global.exception.external.badrequest;

import com.liberty52.product.global.exception.external.notfound.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestForgeryPayException extends BadRequestException {
    private static final Logger logger = LoggerFactory.getLogger(ResourceNotFoundException.class);

    public RequestForgeryPayException() {
        super("결제가 위조된 가능성이 있습니다. 환불처리 후 다시 시도해주세요.");
        logger.error("RequestForgeryPayException: {}, {}, {}", getHttpStatus(), getErrorMessage(), getErrorCode());
    }
}
