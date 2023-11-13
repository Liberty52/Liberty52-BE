package com.liberty52.product.global.exception.external.badrequest;

import com.liberty52.product.global.exception.external.notfound.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CartItemRequiredButOrderItemFoundException extends BadRequestException {
    private static final Logger logger = LoggerFactory.getLogger(ResourceNotFoundException.class);

    public CartItemRequiredButOrderItemFoundException(String customProductId) {
        super(String.format("CartItem의 id가 필요합니다. 요청으로 받은 id(%s)는 OrderItem의 id입니다.", customProductId));
        logger.error("CartItemRequiredButOrderItemFoundException: {}, {}, {}", getHttpStatus(), getErrorMessage(), getErrorCode());
    }
}
