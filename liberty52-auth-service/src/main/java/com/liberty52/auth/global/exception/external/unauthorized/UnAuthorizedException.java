package com.liberty52.auth.global.exception.external.unauthorized;


import com.liberty52.common.exception.external.AbstractApiException;
import com.liberty52.common.exception.external.ErrorCodeImpl;

public class UnAuthorizedException extends AbstractApiException {
  public UnAuthorizedException(String msg) {
    super(ErrorCodeImpl.UNAUTHORIZED, msg);
  }
}
