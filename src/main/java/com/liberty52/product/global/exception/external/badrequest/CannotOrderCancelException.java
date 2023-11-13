package com.liberty52.product.global.exception.external.badrequest;

import com.liberty52.product.global.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.product.service.entity.OrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CannotOrderCancelException extends BadRequestException {
    private static final Logger logger = LoggerFactory.getLogger(ResourceNotFoundException.class);

    public CannotOrderCancelException(OrderStatus nowStatus) {
        super("해당 주문은 취소할 수 없는 상태입니다. 현재상태: " + nowStatus.name());
        logger.error("CannotOrderCancelException: {}, {}, {}", getHttpStatus(), getErrorMessage(), getErrorCode());
    }
}
