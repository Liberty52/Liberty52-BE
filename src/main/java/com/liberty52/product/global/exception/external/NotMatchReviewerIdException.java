package com.liberty52.product.global.exception.external;

import static com.liberty52.product.global.exception.external.ProductErrorCode.NON_MATCH_REVIEWER_ID;

public class NotMatchReviewerIdException extends AbstractApiException{

    public NotMatchReviewerIdException() {
        super(NON_MATCH_REVIEWER_ID);
    }
}
