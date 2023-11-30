package com.liberty52.auth.global.exception.external.unauthorized;

public class AuthNotFoundException extends UnAuthorizedException {
    public AuthNotFoundException() {
        super("등록되지 않은 계정입니다.");
    }
}
