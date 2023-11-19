package com.liberty52.main.global.exception.external.notfound;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductNotFoundByNameException extends ResourceNotFoundException {
    private static final Logger logger = LoggerFactory.getLogger(ResourceNotFoundException.class);

    public ProductNotFoundByNameException(String productName) {
        super("Product", "name", productName);
        logger.error("ProductNotFoundByNameException: {}, {}, {}", getHttpStatus(), getErrorMessage(), getErrorCode());
    }
}
