package com.liberty52.auth.global.exception.external.forbidden;

public class AuthUpdatePasswordForbiddenException extends ForbiddenException {
    public AuthUpdatePasswordForbiddenException() {
        super("해당 링크로는 현재 비밀번호를 변경할 수 없습니다. 다시 메일을 요청해주세요.");
    }
}
