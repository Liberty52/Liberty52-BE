package com.liberty52.main.global.adapter.stablediffusion.error;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public sealed class StableDiffusionError extends RuntimeException {
    public StableDiffusionError(String message, Throwable throwable) {
        super(message, throwable);
    }

    public static final class ResponseBodyStatusIsNotSuccess extends StableDiffusionError {
    }

    public static final class ErrorOccurredBeforeRequest extends StableDiffusionError {
        public ErrorOccurredBeforeRequest(String message, Throwable throwable) {
            super(message, throwable);
        }
    }
}
