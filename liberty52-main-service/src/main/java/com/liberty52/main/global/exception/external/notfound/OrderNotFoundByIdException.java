package com.liberty52.main.global.exception.external.notfound;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderNotFoundByIdException extends ResourceNotFoundException {
    private static final Logger logger = LoggerFactory.getLogger(ResourceNotFoundException.class);

    public OrderNotFoundByIdException(String orderId) {
        super("Order", "id", orderId);
        logger.error("OrderNotFoundByIdException: {}, {}, {}", getHttpStatus(), getErrorMessage(), getErrorCode());
    }
}
