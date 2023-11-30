package com.liberty52.main.global.exception.external.forbidden;

import com.liberty52.main.global.exception.external.notfound.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvalidRoleException extends NotYourResourceException {
    private static final Logger logger = LoggerFactory.getLogger(ResourceNotFoundException.class);

    public InvalidRoleException(String role) {
        super("Role", role);
        logger.error("InvalidRoleException: {}, {}, {}", getHttpStatus(), getErrorMessage(), getErrorCode());
    }
}
