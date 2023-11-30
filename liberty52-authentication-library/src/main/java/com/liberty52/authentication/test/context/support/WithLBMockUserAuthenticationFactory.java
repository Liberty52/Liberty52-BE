package com.liberty52.authentication.test.context.support;

import com.liberty52.authentication.core.Authentication;
import com.liberty52.authentication.core.UserRole;
import com.liberty52.authentication.core.context.AuthenticationContext;
import com.liberty52.authentication.core.context.AuthenticationContextHolder;
import com.liberty52.authentication.core.context.AuthenticationContextHolderStrategy;
import com.liberty52.authentication.core.token.AuthenticationTokenUtils;

public class WithLBMockUserAuthenticationFactory implements WithAuthenticationContextFactory<WithLBMockUser> {

    private AuthenticationContextHolderStrategy strategy = AuthenticationContextHolder
            .getContextHolderStrategy();

    @Override
    public AuthenticationContext createAuthenticationContext(WithLBMockUser withUser) {
        String userId = withUser.id();
        UserRole role = withUser.role();
        Authentication authentication = AuthenticationTokenUtils.getAuthentication(userId, role);

        AuthenticationContext context = strategy.createEmptyContext();
        context.setAuthentication(authentication);

        return context;
    }
}
