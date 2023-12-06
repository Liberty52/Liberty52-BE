package com.liberty52.common.feign.error;

public class FeignUnauthorizedException extends FeignClientException {
    public FeignUnauthorizedException() {
        super(FeignErrorCode.UNAUTHORIZED);
    }
}
