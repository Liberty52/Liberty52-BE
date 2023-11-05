package com.liberty52.authentication.core.context;

import java.util.function.Supplier;

public interface AuthenticationContextHolderStrategy {

    void clearContext();

    AuthenticationContext getContext();

    default Supplier<AuthenticationContext> getDeferredContext() {
        return this::getContext;
    }

    void setContext(AuthenticationContext context);

    default void setDeferredContext(Supplier<AuthenticationContext> deferredContext) {
        setContext(deferredContext.get());
    }

    AuthenticationContext createEmptyContext();

}
