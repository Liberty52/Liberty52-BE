package com.liberty52.product.global.exception.external.notfound;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReplyNotFoundByIdException extends ResourceNotFoundException {
    private static final Logger logger = LoggerFactory.getLogger(ResourceNotFoundException.class);

    public ReplyNotFoundByIdException(String replyId) {
        super("Reply", "id", replyId);
        logger.error("ReplyNotFoundByIdException: {}, {}, {}", getHttpStatus(), getErrorMessage(), getErrorCode());
    }
}
