package com.liberty52.main.global.exception.external.badrequest;

import com.liberty52.main.global.exception.external.notfound.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlreadyCancelOrderException extends BadRequestException {
    private static final Logger logger = LoggerFactory.getLogger(ResourceNotFoundException.class);

    public AlreadyCancelOrderException() {
        super("해당 주문은 이미 취소되었거나 취소 요청된 주문입니다.");
        logger.error("AlreadyCancelOrderException: {}, {}, {}", getHttpStatus(), getErrorMessage(), getErrorCode());
    }
}
