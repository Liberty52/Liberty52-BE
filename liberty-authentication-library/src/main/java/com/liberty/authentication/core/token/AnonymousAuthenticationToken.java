package com.liberty.authentication.core.token;

import com.liberty.authentication.core.UserRole;
import org.springframework.util.Assert;

import java.util.Collection;

public class AnonymousAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;

    public AnonymousAuthenticationToken(Object principal, Collection<UserRole> authorities) {
        super(authorities);
        Assert.isTrue(principal != null && !"".equals(principal), "principal cannot be null or empty");
        this.principal = principal;
        setAuthentication(true);
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public String toString() {
        return "AnonymousAuthenticationToken{" +
                "principal=" + principal +
                '}';
    }
}
