package com.liberty52.product.global.exception.external.badrequest;

public class DistributedLockException extends BadRequestException {
    public DistributedLockException() {
        super("현재 동시 요청자가 많습니다. 다시 요청해주세요.");
    }
}
