package com.liberty52.auth.global.exception.external.unauthorized;

import com.liberty52.common.exception.external.unauthorized.UnauthorizedException;

public class AuthNotFoundException extends UnauthorizedException {
    public AuthNotFoundException() {
        super("등록되지 않은 계정입니다.");
    }
}
