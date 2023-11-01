package com.liberty.authentication.core.token;

import com.liberty.authentication.core.UserRole;
import com.liberty.authentication.core.principal.UserPrincipal;
import org.springframework.util.Assert;

import java.util.Collection;

public class AnonymousAuthenticationToken extends AbstractAuthenticationToken {

    private final UserPrincipal principal;

    public AnonymousAuthenticationToken(UserPrincipal principal, Collection<UserRole> authorities) {
        super(authorities);
        Assert.isTrue(principal != null, "principal cannot be null or empty");
        this.principal = principal;
        setAuthentication(true);
    }

    @Override
    public UserPrincipal getPrincipal() {
        return this.principal;
    }

    @Override
    public String toString() {
        return "AnonymousAuthenticationToken{" +
                "principal=" + principal +
                '}';
    }
}
