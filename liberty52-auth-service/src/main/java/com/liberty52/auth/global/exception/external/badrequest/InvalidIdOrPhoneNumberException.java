package com.liberty52.auth.global.exception.external.badrequest;

import com.liberty52.common.exception.external.badrequest.BadRequestException;

public class InvalidIdOrPhoneNumberException extends BadRequestException {

  public InvalidIdOrPhoneNumberException() {
    super("입력하신 정보와 일치하는 계정이 없습니다.");
  }
}
