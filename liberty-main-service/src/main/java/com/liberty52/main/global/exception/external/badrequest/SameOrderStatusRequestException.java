package com.liberty52.main.global.exception.external.badrequest;

import com.liberty52.main.global.exception.external.notfound.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SameOrderStatusRequestException extends BadRequestException {
    private static final Logger logger = LoggerFactory.getLogger(ResourceNotFoundException.class);

    public SameOrderStatusRequestException() {
        super("현재와 같은 상태 변경 요청입니다.");
        logger.error("SameOrderStatusRequestException: {}, {}, {}", getHttpStatus(), getErrorMessage(), getErrorCode());
    }
}
