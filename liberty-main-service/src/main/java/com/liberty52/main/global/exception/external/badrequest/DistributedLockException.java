package com.liberty52.main.global.exception.external.badrequest;

import com.liberty52.main.global.exception.external.notfound.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DistributedLockException extends BadRequestException {
    private static final Logger logger = LoggerFactory.getLogger(ResourceNotFoundException.class);

    public DistributedLockException() {
        super("현재 동시 요청자가 많습니다. 다시 요청해주세요.");
        logger.error("DistributedLockException: {}, {}, {}", getHttpStatus(), getErrorMessage(), getErrorCode());
    }
}
