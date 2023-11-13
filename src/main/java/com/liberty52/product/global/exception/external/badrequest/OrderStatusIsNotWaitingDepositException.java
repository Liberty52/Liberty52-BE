package com.liberty52.product.global.exception.external.badrequest;

import com.liberty52.product.global.exception.external.notfound.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderStatusIsNotWaitingDepositException extends BadRequestException{
  private static final Logger logger = LoggerFactory.getLogger(ResourceNotFoundException.class);

  public OrderStatusIsNotWaitingDepositException(){
    super("입금대기 상태가 아닙니다.");
    logger.warn("OrderStatusIsNotWaitingDepositException: {}, {}, {}", getHttpStatus(), getErrorMessage(), getErrorCode());
  }
}
