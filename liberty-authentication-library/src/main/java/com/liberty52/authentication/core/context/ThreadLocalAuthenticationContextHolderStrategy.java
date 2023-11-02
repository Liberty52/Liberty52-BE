package com.liberty52.authentication.core.context;

import org.springframework.util.Assert;

import java.util.function.Supplier;

final class ThreadLocalAuthenticationContextHolderStrategy implements AuthenticationContextHolderStrategy {

    private static final ThreadLocal<Supplier<AuthenticationContext>> contextHolder = new ThreadLocal<>();

    @Override
    public void clearContext() {
        contextHolder.remove();
    }

    @Override
    public AuthenticationContext getContext() {
        return getDeferredContext().get();
    }

    @Override
    public Supplier<AuthenticationContext> getDeferredContext() {
        Supplier<AuthenticationContext> result = contextHolder.get();
        if (result == null) {
            AuthenticationContext context = createEmptyContext();
            result = () -> context;
            contextHolder.set(result);
        }
        return result;
    }

    @Override
    public void setContext(AuthenticationContext context) {
        Assert.notNull(context, "AuthenticationContext cannot be null.");
        contextHolder.set(() -> context);
    }

    @Override
    public void setDeferredContext(Supplier<AuthenticationContext> deferredContext) {
        Assert.notNull(deferredContext, "AuthenticationContext Supplier cannot be null.");
        Supplier<AuthenticationContext> context = () -> {
            AuthenticationContext result = deferredContext.get();
            Assert.notNull(result, "DeferredContext returned null and is not allowed.");
            return result;
        };
        contextHolder.set(context);
    }

    @Override
    public AuthenticationContext createEmptyContext() {
        return new AuthenticationContextImpl();
    }
}
