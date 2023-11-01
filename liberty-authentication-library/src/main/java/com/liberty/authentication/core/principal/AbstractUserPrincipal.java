package com.liberty.authentication.core.principal;

import com.liberty.authentication.core.UserRole;

abstract class AbstractUserPrincipal implements UserPrincipal {

    private final String userId;
    private final UserRole role;

    protected AbstractUserPrincipal(String userId, UserRole role) {
        this.userId = userId;
        this.role = role;
    }

    @Override
    public String getUserId() {
        return this.userId;
    }

    @Override
    public UserRole getRole() {
        return this.role;
    }
}
