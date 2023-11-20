package com.liberty52.auth.global.exception.external.internalservererror;

public class InvalidResourceConstraintException extends InternalServerErrorException {
    public InvalidResourceConstraintException(RuntimeException e) {
        super(e.getClass().getSimpleName());
    }
}
