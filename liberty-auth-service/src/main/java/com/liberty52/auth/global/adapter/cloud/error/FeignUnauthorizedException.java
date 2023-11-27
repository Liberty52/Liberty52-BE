package com.liberty52.auth.global.adapter.cloud.error;

public class FeignUnauthorizedException extends FeignClientException {
    public FeignUnauthorizedException() {
        super(FeignErrorCode.UNAUTHORIZED);
    }
}
