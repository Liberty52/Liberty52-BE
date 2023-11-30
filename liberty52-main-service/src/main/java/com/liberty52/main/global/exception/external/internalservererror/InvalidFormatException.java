package com.liberty52.main.global.exception.external.internalservererror;

import com.liberty52.common.exception.external.notfound.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvalidFormatException extends InternalServerErrorException {
    private static final Logger logger = LoggerFactory.getLogger(ResourceNotFoundException.class);

    public InvalidFormatException() {
        super("데이터가 올바른 형태를 갖추지 못했습니다.");
        logger.error("InvalidFormatException: {}, {}, {}", getHttpStatus(), getErrorMessage(), getErrorCode());
    }

}
