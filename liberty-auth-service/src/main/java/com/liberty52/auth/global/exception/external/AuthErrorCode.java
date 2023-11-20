package com.liberty52.auth.global.exception.external;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum AuthErrorCode implements ErrorCode{
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_FOUND(HttpStatus.NOT_FOUND),
    FORBIDDEN(HttpStatus.FORBIDDEN),
    BAD_REQUEST(HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED);

    private final HttpStatus httpStatus;
    private final String errorMessage;

    private final String errorCode = "A-" + "0".repeat(Math.max(4-String.valueOf(this.ordinal() + 1).length(), 0)) + (this.ordinal() + 1);

    AuthErrorCode(HttpStatus httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    AuthErrorCode(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        this.errorMessage = "";
    }

    public String getErrorName() {
        return this.name();
    }
}