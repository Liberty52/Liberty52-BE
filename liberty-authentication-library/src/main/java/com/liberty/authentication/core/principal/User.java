package com.liberty.authentication.core.principal;

import com.liberty.authentication.core.UserRole;

public interface User {
    String getUserId();

    UserRole getRole();
}
