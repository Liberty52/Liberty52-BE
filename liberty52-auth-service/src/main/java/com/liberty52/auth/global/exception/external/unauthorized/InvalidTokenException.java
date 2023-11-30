package com.liberty52.auth.global.exception.external.unauthorized;

public class InvalidTokenException extends UnAuthorizedException {
  public InvalidTokenException() {
    super("토큰이 존재하지 않습니다.");
  }
}
