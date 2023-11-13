package com.liberty52.product.global.exception.external.forbidden;

import com.liberty52.product.global.exception.external.notfound.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotYourOrderException extends NotYourResourceException {
    private static final Logger logger = LoggerFactory.getLogger(ResourceNotFoundException.class);

    public NotYourOrderException(String id) {
        super("Order", id);
        logger.error("NotYourOrderException: {}, {}, {}", getHttpStatus(), getErrorMessage(), getErrorCode());
    }
}
