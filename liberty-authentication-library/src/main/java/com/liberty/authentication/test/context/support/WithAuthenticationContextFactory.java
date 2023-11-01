package com.liberty.authentication.test.context.support;

import com.liberty.authentication.core.context.AuthenticationContext;

import java.lang.annotation.Annotation;

public interface WithAuthenticationContextFactory<A extends Annotation> {
    AuthenticationContext createAuthenticationContext(A annotation);
}
