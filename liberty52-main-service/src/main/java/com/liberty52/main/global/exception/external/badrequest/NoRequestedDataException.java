package com.liberty52.main.global.exception.external.badrequest;

import com.liberty52.common.exception.external.badrequest.BadRequestException;
import com.liberty52.common.exception.external.notfound.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoRequestedDataException extends BadRequestException {
    private static final Logger logger = LoggerFactory.getLogger(ResourceNotFoundException.class);

    public NoRequestedDataException() {
        super("요청 데이터가 존재하지 않습니다.");
        logger.error("NoRequestedDataException: {}, {}, {}", getHttpStatus(), getErrorMessage(), getErrorCode());
    }
}
