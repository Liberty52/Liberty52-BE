package com.liberty52.auth.global.exception.external.unauthorized;

import com.liberty52.common.exception.external.unauthorized.UnauthorizedException;

public class InvalidTokenException extends UnauthorizedException {
  public InvalidTokenException() {
    super("토큰이 존재하지 않습니다.");
  }
}
