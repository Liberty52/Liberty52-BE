package com.liberty.authentication.core.token;

import com.liberty.authentication.core.UserRole;

import java.util.Collection;

public class UserAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;

    public UserAuthenticationToken(Object principal, Collection<UserRole> authorities) {
        super(authorities);
        this.principal = principal;
        setAuthentication(true);
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public String toString() {
        return "UserAuthenticationToken{" +
                "principal=" + principal +
                '}';
    }
}
