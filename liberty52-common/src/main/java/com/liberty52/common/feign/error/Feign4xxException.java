package com.liberty52.common.feign.error;

public class Feign4xxException extends FeignClientException {
    public Feign4xxException() {
        super(FeignErrorCode.ERROR_4XX);
    }
}
