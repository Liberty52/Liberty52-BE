package com.liberty52.authentication.core.token;

import com.liberty52.authentication.core.Authentication;
import com.liberty52.authentication.core.UserRole;
import com.liberty52.authentication.core.principal.AnonymousUser;
import com.liberty52.authentication.core.principal.AuthUser;
import com.liberty52.authentication.core.principal.GuestUser;

import java.util.List;

public class AuthenticationTokenUtils {
    public static Authentication getAuthentication(String userId, UserRole role) {
        Authentication authentication;
        if (userId == null || userId.isBlank() || UserRole.ANONYMOUS.equals(role)) {
            authentication = new AnonymousAuthenticationToken(new AnonymousUser("anonymous"), List.of(UserRole.ANONYMOUS));
        } else if (UserRole.GUEST.equals(role)) {
            authentication = new UserAuthenticationToken(new GuestUser(userId), List.of(role));
        } else {
            authentication = new UserAuthenticationToken(new AuthUser(userId, role), List.of(role));
        }
        return authentication;
    }
}
