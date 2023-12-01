package com.liberty52.common.exception.external;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

    HttpStatus getHttpStatus();

    String getErrorCode();

    String getErrorName();

    String getErrorMessage();
}
