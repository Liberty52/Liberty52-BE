package com.liberty52.auth.global.exception.external.badrequest;

public class InvalidSocialLoginCodeAccessedException extends BadRequestException {

    public InvalidSocialLoginCodeAccessedException(
            ) {
        super("불가능한 소셜 로그인 코드가 도달했습니다.");
    }
}
