package com.liberty52.auth.global.exception.external.unauthorized;

public class AuthUnauthorizedException extends UnAuthorizedException {
    public AuthUnauthorizedException() {
        super("회원정보가 일치하지 않습니다.");
    }
}
