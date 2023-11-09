package com.liberty52.product.global.exception.external.badrequest;

public class NoRequestedDataException extends BadRequestException{
    public NoRequestedDataException() {
        super("요청 데이터가 존재하지 않습니다.");
    }
}
