package com.liberty52.main.global.exception.external.badrequest;

import com.liberty52.common.exception.external.badrequest.BadRequestException;
import com.liberty52.common.exception.external.notfound.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbnormalOrderStatusRequestException extends BadRequestException {
    private static final Logger logger = LoggerFactory.getLogger(ResourceNotFoundException.class);

    public AbnormalOrderStatusRequestException() {
        super("비정상적인 상태 변경 요청입니다.");
        logger.error("AbnormalOrderStatusRequestException: {}, {}, {}", getHttpStatus(), getErrorMessage(), getErrorCode());
    }
}
