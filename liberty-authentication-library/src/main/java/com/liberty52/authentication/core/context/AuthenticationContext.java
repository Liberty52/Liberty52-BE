package com.liberty52.authentication.core.context;

import com.liberty52.authentication.core.Authentication;

public interface AuthenticationContext {

    Authentication getAuthentication();

    void setAuthentication(Authentication authentication);

}
