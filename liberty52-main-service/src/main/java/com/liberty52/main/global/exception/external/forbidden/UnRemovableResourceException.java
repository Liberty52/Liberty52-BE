package com.liberty52.main.global.exception.external.forbidden;

import com.liberty52.common.exception.external.forbidden.ForbiddenException;
import com.liberty52.common.exception.external.notfound.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnRemovableResourceException extends ForbiddenException {
    private static final Logger logger = LoggerFactory.getLogger(ResourceNotFoundException.class);

    public UnRemovableResourceException(String resourceName, String resourceId) {
        super(String.format("%s cannot be removed. The id of %s was %s", resourceName, resourceName, resourceId));
        logger.error("UnRemovableResourceException: {}, {}, {}", getHttpStatus(), getErrorMessage(), getErrorCode());
    }
}
