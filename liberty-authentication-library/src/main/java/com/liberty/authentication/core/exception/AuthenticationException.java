package com.liberty.authentication.core.exception;

import org.springframework.http.HttpStatus;

public class AuthenticationException extends RuntimeException {

    private final HttpStatus status;

    public AuthenticationException(AuthenticationError error) {
        super(error.getMessage());
        this.status = error.getStatus();
    }

    public AuthenticationException(AuthenticationError error, String msg) {
        super(msg);
        this.status = error.getStatus();
    }

    public AuthenticationException(String msg) {
        super(msg);
        this.status = HttpStatus.UNAUTHORIZED;
    }

    public HttpStatus getStatus() {
        return this.status;
    }
}
