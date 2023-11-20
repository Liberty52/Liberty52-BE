package com.liberty52.auth.global.exception.external.badrequest;

public class AuthWithInvalidPasswordException extends BadRequestException {

  public AuthWithInvalidPasswordException() {
    super("기존 비밀번호가 일치하지 않습니다");
  }
}
