package com.liberty52.authentication.core.exception;

import org.springframework.http.HttpStatus;

public enum AuthenticationError {
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "You are an unauthorized user."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "You cannot access.");

    private final HttpStatus status;
    private final String message;

    AuthenticationError(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
