package com.liberty52.product.global.exception.external.badrequest;

import com.liberty52.product.global.exception.external.notfound.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReviewCannotWriteInCartException extends BadRequestException {
  private static final Logger logger = LoggerFactory.getLogger(ResourceNotFoundException.class);

    public ReviewCannotWriteInCartException() {
        super("구매한 제품에만 리뷰를 남길 수 있습니다");
        logger.error("ReviewCannotWriteInCartException: {}, {}, {}", getHttpStatus(), getErrorMessage(), getErrorCode());
    }
}
