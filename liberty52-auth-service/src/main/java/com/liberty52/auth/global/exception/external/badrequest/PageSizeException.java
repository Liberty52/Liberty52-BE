package com.liberty52.auth.global.exception.external.badrequest;

import com.liberty52.common.exception.external.badrequest.BadRequestException;

public class PageSizeException extends BadRequestException {

  public PageSizeException() {
    super(("유효하지 않은 페이지 크기입니다."));
  }
}
