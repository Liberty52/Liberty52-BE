package com.liberty52.main.global.exception.external.badrequest;

import com.liberty52.main.global.exception.external.notfound.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CannotAccessOrderException extends BadRequestException {
    private static final Logger logger = LoggerFactory.getLogger(ResourceNotFoundException.class);

    public CannotAccessOrderException() {
        super("해당 주문에 접근할 수 없습니다.");
        logger.error("CannotAccessOrderException: {}, {}, {}", getHttpStatus(), getErrorMessage(), getErrorCode());
    }
}