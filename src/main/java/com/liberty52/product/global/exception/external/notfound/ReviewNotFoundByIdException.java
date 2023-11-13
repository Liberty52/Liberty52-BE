package com.liberty52.product.global.exception.external.notfound;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ReviewNotFoundByIdException extends ResourceNotFoundException {
    private static final Logger logger = LoggerFactory.getLogger(ResourceNotFoundException.class);

    public ReviewNotFoundByIdException(String id) {
        super("Review", "id", id);
        logger.error("ReviewNotFoundByIdException: {}, {}, {}", getHttpStatus(), getErrorMessage(), getErrorCode());
    }
}
