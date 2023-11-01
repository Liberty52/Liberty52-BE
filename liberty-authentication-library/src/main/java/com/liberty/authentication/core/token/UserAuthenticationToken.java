package com.liberty.authentication.core.token;

import com.liberty.authentication.core.UserRole;
import com.liberty.authentication.core.principal.UserPrincipal;

import java.util.Collection;

public class UserAuthenticationToken extends AbstractAuthenticationToken {

    private final UserPrincipal principal;

    public UserAuthenticationToken(UserPrincipal principal, Collection<UserRole> authorities) {
        super(authorities);
        this.principal = principal;
        setAuthentication(true);
    }

    @Override
    public UserPrincipal getPrincipal() {
        return this.principal;
    }

    @Override
    public String toString() {
        return "UserAuthenticationToken{" +
                "principal=" + principal +
                '}';
    }
}
