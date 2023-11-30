package com.liberty52.main.global.exception.external.badrequest;

import com.liberty52.common.exception.external.badrequest.BadRequestException;
import com.liberty52.common.exception.external.notfound.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CartAddInvalidItemException extends BadRequestException {
    private static final Logger logger = LoggerFactory.getLogger(ResourceNotFoundException.class);

    public CartAddInvalidItemException() {
        super("해당 상품은 장바구니에 담길 수 없습니다. (사유 : 이미 주문된 제품)");
        logger.error("CartAddInvalidItemException: {}, {}, {}", getHttpStatus(), getErrorMessage(), getErrorCode());
    }
}
