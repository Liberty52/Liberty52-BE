package com.liberty.authentication.core;

import java.security.Principal;
import java.util.Collection;

public interface Authentication extends Principal {

    Collection<UserRole> getAuthorities();

    Object getPrincipal();

    boolean isAuthenticated();

    void setAuthentication(boolean isAuthentication);

}
