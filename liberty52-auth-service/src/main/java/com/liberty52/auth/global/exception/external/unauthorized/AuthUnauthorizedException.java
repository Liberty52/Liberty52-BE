package com.liberty52.auth.global.exception.external.unauthorized;

import com.liberty52.common.exception.external.unauthorized.UnauthorizedException;

public class AuthUnauthorizedException extends UnauthorizedException {
    public AuthUnauthorizedException() {
        super("회원정보가 일치하지 않습니다.");
    }
}
