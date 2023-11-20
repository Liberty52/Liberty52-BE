package com.liberty52.auth.global.exception.external.badrequest;

public class AuthWithSuchEmailAlreadyExistsException extends BadRequestException {
    public AuthWithSuchEmailAlreadyExistsException() {
        super("이미 존재하는 이메일입니다.");
    }
}
