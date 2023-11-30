package com.liberty52.main.global.exception.external.badrequest;

import com.liberty52.common.exception.external.badrequest.BadRequestException;
import com.liberty52.main.global.exception.external.notfound.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReviewAlreadyExistByCustomProductException extends BadRequestException {
    private static final Logger logger = LoggerFactory.getLogger(ResourceNotFoundException.class);

    public ReviewAlreadyExistByCustomProductException() {
        super("이미 제품에 대한 리뷰가 존재합니다.");
        logger.error("ReviewAlreadyExistByCustomProductException: {}, {}, {}", getHttpStatus(), getErrorMessage(), getErrorCode());
    }
}
