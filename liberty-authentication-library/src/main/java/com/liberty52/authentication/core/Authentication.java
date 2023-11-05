package com.liberty52.authentication.core;

import com.liberty52.authentication.core.principal.User;

import java.security.Principal;
import java.util.Collection;

public interface Authentication extends Principal {

    Collection<UserRole> getAuthorities();

    User getPrincipal();

    boolean isAuthenticated();

    void setAuthentication(boolean isAuthentication);

}
