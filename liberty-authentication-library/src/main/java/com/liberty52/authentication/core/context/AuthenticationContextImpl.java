package com.liberty52.authentication.core.context;

import com.liberty52.authentication.core.Authentication;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

public class AuthenticationContextImpl implements AuthenticationContext {

    private Authentication authentication;

    public AuthenticationContextImpl() {
    }

    public AuthenticationContextImpl(Authentication authentication) {
        this.authentication = authentication;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthenticationContextImpl that = (AuthenticationContextImpl) o;
        if (this.authentication == null && that.authentication == null) {
            return true;
        }
        return Objects.equals(authentication, that.authentication);
    }

    @Override
    public int hashCode() {
        return ObjectUtils.nullSafeHashCode(authentication);
    }

    @Override
    public Authentication getAuthentication() {
        return this.authentication;
    }

    @Override
    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }
}
