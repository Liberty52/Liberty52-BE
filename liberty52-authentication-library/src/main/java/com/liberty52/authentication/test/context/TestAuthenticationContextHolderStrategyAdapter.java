package com.liberty52.authentication.test.context;

import com.liberty52.authentication.core.context.AuthenticationContext;
import com.liberty52.authentication.core.context.AuthenticationContextHolder;
import com.liberty52.authentication.core.context.AuthenticationContextHolderStrategy;

public class TestAuthenticationContextHolderStrategyAdapter implements AuthenticationContextHolderStrategy  {
    @Override
    public void clearContext() {
        TestAuthenticationContextHolder.clearContext();
    }

    @Override
    public AuthenticationContext getContext() {
        return TestAuthenticationContextHolder.getContext();
    }

    @Override
    public void setContext(AuthenticationContext context) {
        TestAuthenticationContextHolder.setContext(context);
    }

    @Override
    public AuthenticationContext createEmptyContext() {
        return AuthenticationContextHolder.createEmptyContext();
    }
}
