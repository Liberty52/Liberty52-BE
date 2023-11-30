package com.liberty52.auth.global.exception.external.unauthorized;

import com.liberty52.auth.global.exception.external.AbstractApiException;
import com.liberty52.auth.global.exception.external.AuthErrorCode;

public class UnAuthorizedException extends AbstractApiException {
  public UnAuthorizedException(String msg) {
    super(AuthErrorCode.UNAUTHORIZED, msg);
  }
}
