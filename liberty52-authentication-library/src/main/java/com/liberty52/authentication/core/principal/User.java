package com.liberty52.authentication.core.principal;

import com.liberty52.authentication.core.UserRole;

public interface User {
    String getUserId();

    UserRole getRole();
}
