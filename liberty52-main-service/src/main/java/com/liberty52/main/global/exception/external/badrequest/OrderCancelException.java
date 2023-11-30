package com.liberty52.main.global.exception.external.badrequest;

import com.liberty52.common.exception.external.badrequest.BadRequestException;
import com.liberty52.main.global.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.main.service.entity.OrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderCancelException extends BadRequestException {
    private static final Logger logger = LoggerFactory.getLogger(ResourceNotFoundException.class);

    public OrderCancelException(OrderStatus nowStatus) {
        super("해당 주문은 취소할 수 없는 상태입니다. 현재상태: " + nowStatus.getKoName());
        logger.warn("OrderCancelException: {}, {}, {}", getHttpStatus(), getErrorMessage(), getErrorCode());
    }
}
