package com.liberty52.main.global.exception.external.forbidden;

import com.liberty52.common.exception.external.forbidden.NotYourResourceException;
import com.liberty52.main.global.exception.external.notfound.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotYourCustomProductException extends NotYourResourceException {
    private static final Logger logger = LoggerFactory.getLogger(ResourceNotFoundException.class);

    public NotYourCustomProductException(String id) {
        super("CUSTOM_PRODUCT", id);
        logger.error("NotYourCustomProductException: {}, {}, {}", getHttpStatus(), getErrorMessage(), getErrorCode());
    }
}
