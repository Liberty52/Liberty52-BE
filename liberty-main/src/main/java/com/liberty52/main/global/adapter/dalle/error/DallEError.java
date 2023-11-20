package com.liberty52.main.global.adapter.dalle.error;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public sealed abstract class DallEError extends RuntimeException {
    public DallEError(String message, Throwable throwable) {
        super(message, throwable);
    }

    public static final class ErrorOccurredBeforeRequest extends DallEError {
        public ErrorOccurredBeforeRequest(String message, Throwable throwable) {
            super(message, throwable);
        }
    }
}
