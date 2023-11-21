package com.liberty52.main.global.exception.external.internalservererror;

import com.liberty52.main.global.exception.external.notfound.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfirmPaymentException extends InternalServerErrorException {
    private static final Logger logger = LoggerFactory.getLogger(ResourceNotFoundException.class);

    public ConfirmPaymentException() {
        super("결제 시스템에 오류가 발생하여 응답하기 어렵습니다. 관리자에게 문의해주세요.");
        logger.error("ConfirmPaymentException: {}, {}, {}", getHttpStatus(), getErrorMessage(), getErrorCode());
    }
}
