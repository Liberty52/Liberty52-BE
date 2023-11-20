package com.liberty52.main.global.exception.external;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

    HttpStatus getHttpStatus();

    String getErrorCode();

    String getErrorName();

    String getErrorMessage();
}
