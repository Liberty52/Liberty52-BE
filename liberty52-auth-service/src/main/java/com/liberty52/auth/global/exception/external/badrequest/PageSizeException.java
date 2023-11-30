package com.liberty52.auth.global.exception.external.badrequest;

public class PageSizeException extends BadRequestException {

  public PageSizeException() {
    super(("유효하지 않은 페이지 크기입니다."));
  }
}
