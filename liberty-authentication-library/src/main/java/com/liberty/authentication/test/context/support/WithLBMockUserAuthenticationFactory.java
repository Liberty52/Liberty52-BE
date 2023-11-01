package com.liberty.authentication.test.context.support;

import com.liberty.authentication.core.Authentication;
import com.liberty.authentication.core.UserRole;
import com.liberty.authentication.core.context.AuthenticationContext;
import com.liberty.authentication.core.context.AuthenticationContextHolder;
import com.liberty.authentication.core.context.AuthenticationContextHolderStrategy;

import static com.liberty.authentication.core.token.AuthenticationTokenUtils.getAuthentication;

public class WithLBMockUserAuthenticationFactory implements WithAuthenticationContextFactory<WithLBMockUser> {

    private AuthenticationContextHolderStrategy strategy = AuthenticationContextHolder
            .getContextHolderStrategy();

    @Override
    public AuthenticationContext createAuthenticationContext(WithLBMockUser withUser) {
        String userId = withUser.id();
        UserRole role = withUser.role();
        Authentication authentication = getAuthentication(userId, role);

        AuthenticationContext context = strategy.createEmptyContext();
        context.setAuthentication(authentication);

        return context;
    }
}
