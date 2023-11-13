package com.liberty52.product.global.exception.external.forbidden;

import com.liberty52.product.global.exception.external.notfound.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotYourReviewException extends NotYourResourceException {
    private static final Logger logger = LoggerFactory.getLogger(ResourceNotFoundException.class);

    public NotYourReviewException(String id) {
        super("Review", id);
        logger.error("NotYourReviewException: {}, {}, {}", getHttpStatus(), getErrorMessage(), getErrorCode());
    }
}
