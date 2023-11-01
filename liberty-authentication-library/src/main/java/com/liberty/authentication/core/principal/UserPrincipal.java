package com.liberty.authentication.core.principal;

import com.liberty.authentication.core.UserRole;

public interface UserPrincipal {
    String getUserId();

    UserRole getRole();
}
