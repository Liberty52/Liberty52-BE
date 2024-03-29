package com.liberty52.authentication.test.context;

import com.liberty52.authentication.core.Authentication;
import com.liberty52.authentication.core.context.AuthenticationContext;
import com.liberty52.authentication.core.context.AuthenticationContextHolder;

public class TestAuthenticationContextHolder {

    private static final ThreadLocal<AuthenticationContext> contextHolder = new ThreadLocal<>();

    private TestAuthenticationContextHolder() {}

    public static void clearContext() {
        contextHolder.remove();
        AuthenticationContextHolder.clearContext();
    }

    public static AuthenticationContext getContext() {
        AuthenticationContext context = contextHolder.get();
        if (context == null) {
            context = getDefaultContext();
            contextHolder.set(context);
        }
        return context;
    }

    public static void setContext(AuthenticationContext context) {
        contextHolder.set(context);
        AuthenticationContextHolder.setContext(context);
    }

    public static void setAuthentication(Authentication authentication) {
        AuthenticationContext context = AuthenticationContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        setContext(context);
    }

    private static AuthenticationContext getDefaultContext() {
        return AuthenticationContextHolder.getContext();
    }

}
