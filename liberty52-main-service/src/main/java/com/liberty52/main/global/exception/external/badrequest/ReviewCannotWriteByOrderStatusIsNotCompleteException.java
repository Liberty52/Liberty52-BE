package com.liberty52.main.global.exception.external.badrequest;

import com.liberty52.common.exception.external.badrequest.BadRequestException;
import com.liberty52.main.global.exception.external.notfound.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReviewCannotWriteByOrderStatusIsNotCompleteException extends BadRequestException {
    private static final Logger logger = LoggerFactory.getLogger(ResourceNotFoundException.class);

    public ReviewCannotWriteByOrderStatusIsNotCompleteException() {
        super("배송 완료된 제품에만 리뷰를 남길 수 있습니다");
        logger.error("ReviewAlreadyExistByCustomProductException: {}, {}, {}", getHttpStatus(), getErrorMessage(), getErrorCode());
    }
}
