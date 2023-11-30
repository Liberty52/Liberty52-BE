package com.liberty52.main.global.adapter.portone.exception;

import com.liberty52.main.global.exception.external.internalservererror.InternalServerErrorException;

public class PortOneErrorException extends InternalServerErrorException {
    public PortOneErrorException(String msg) {
        super("PortOne " + msg);
    }
}
