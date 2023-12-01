package com.liberty52.auth.global.exception.external.badrequest;

import com.liberty52.common.exception.external.badrequest.BadRequestException;

public class AuthWithInvalidPasswordException extends BadRequestException {

  public AuthWithInvalidPasswordException() {
    super("기존 비밀번호가 일치하지 않습니다");
  }
}
