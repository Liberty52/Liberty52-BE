package com.liberty52.main.global.exception.external.badrequest;

import com.liberty52.main.global.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.main.service.entity.OrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderRefundException extends BadRequestException {
    private static final Logger logger = LoggerFactory.getLogger(ResourceNotFoundException.class);

    public OrderRefundException(OrderStatus nowStatus) {
        super("해당 주문은 환불 처리할 수 없는 상태입니다. 현재상태: " + nowStatus.name());
        logger.warn("OrderRefundException: {}, {}, {}", getHttpStatus(), getErrorMessage(), getErrorCode());
    }

    public OrderRefundException(String msg) {
        super(msg);
    }
}
