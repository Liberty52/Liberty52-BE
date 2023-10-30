package com.liberty.authentication.core.context;

import com.liberty.authentication.core.Authentication;

public interface AuthenticationContext {

    Authentication getAuthentication();

    void setAuthentication(Authentication authentication);

}
