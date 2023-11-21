package com.liberty52.main.global.util;

import org.slf4j.Logger;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.function.Consumer;

public class Retries {
    private static final Long DEFAULT_MAX_ATTEMPTS = 3L;
    private static final Duration DEFUALT_DURATION = Duration.ofMillis(300);

    public static Retry with(Long maxAttempts, Duration duration, Consumer<Retry.RetrySignal> doBeforeRetry) {
        return Retry.backoff(maxAttempts, duration)
                .doBeforeRetry(doBeforeRetry);
    }

    public static Retry withDefault(Logger logger) {
        return with(DEFAULT_MAX_ATTEMPTS, DEFUALT_DURATION, defaultBeforeTry(logger));
    }

    private static Consumer<Retry.RetrySignal> defaultBeforeTry(Logger logger) {
        return retrySignal -> logger.info("Retrying... " + retrySignal.totalRetries());
    }
}
