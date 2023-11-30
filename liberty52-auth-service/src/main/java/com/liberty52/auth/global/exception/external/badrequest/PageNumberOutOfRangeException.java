package com.liberty52.auth.global.exception.external.badrequest;

public class PageNumberOutOfRangeException extends BadRequestException {

  public PageNumberOutOfRangeException() {
    super(("유효하지 않은 페이지 번호입니다."));
  }
}
