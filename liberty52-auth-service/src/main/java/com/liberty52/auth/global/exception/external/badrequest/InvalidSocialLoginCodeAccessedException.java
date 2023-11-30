package com.liberty52.auth.global.exception.external.badrequest;

import com.liberty52.common.exception.external.badrequest.BadRequestException;

public class InvalidSocialLoginCodeAccessedException extends BadRequestException {

    public InvalidSocialLoginCodeAccessedException(
            ) {
        super("불가능한 소셜 로그인 코드가 도달했습니다.");
    }
}
