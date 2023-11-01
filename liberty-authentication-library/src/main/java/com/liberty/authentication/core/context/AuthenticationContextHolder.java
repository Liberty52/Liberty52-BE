package com.liberty.authentication.core.context;

import org.springframework.lang.NonNull;

import java.util.function.Supplier;

public class AuthenticationContextHolder {

    private static AuthenticationContextHolderStrategy strategy;

    static {
        initialize();
    }

    private static void initialize() {
        strategy = new ThreadLocalAuthenticationContextHolderStrategy();
    }

    public static void clearContext() {
        strategy.clearContext();
    }

    public static AuthenticationContext getContext() {
        return strategy.getContext();
    }

    public static Supplier<AuthenticationContext> getDeferredContext() {
        return strategy.getDeferredContext();
    }

    public static void setContext(AuthenticationContext context) {
        strategy.setContext(context);
    }

    public static void setDeferredContext(Supplier<AuthenticationContext> deferredContext) {
        strategy.setDeferredContext(deferredContext);
    }

    public static AuthenticationContextHolderStrategy getContextHolderStrategy() {
        return strategy;
    }

    public static void setContextHolderStrategy(@NonNull AuthenticationContextHolderStrategy s) {
        strategy = s;
    }

    public static AuthenticationContext createEmptyContext() {
        return strategy.createEmptyContext();
    }
}
