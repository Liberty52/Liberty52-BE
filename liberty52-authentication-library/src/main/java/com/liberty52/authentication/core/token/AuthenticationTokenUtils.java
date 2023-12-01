package com.liberty52.authentication.core.token;

import com.liberty52.authentication.core.Authentication;
import com.liberty52.authentication.core.UserRole;
import com.liberty52.authentication.core.principal.AnonymousUser;
import com.liberty52.authentication.core.principal.AuthUser;
import com.liberty52.authentication.core.principal.GuestUser;

import java.util.List;
import java.util.Optional;

public final class AuthenticationTokenUtils {
    private AuthenticationTokenUtils() {}

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static Authentication getAuthentication(final String userId, final Optional<UserRole> role) {
        Authentication authentication;
        if (userId == null || userId.isBlank() || UserRole.ANONYMOUS.equals(role.orElse(null))) {
            authentication = new AnonymousAuthenticationToken(
                    new AnonymousUser("anonymous"), List.of(UserRole.ANONYMOUS));
        } else if (UserRole.GUEST.equals(role.orElse(null)) || hasGuestPrefix(userId)) {
            authentication = new UserAuthenticationToken(
                    new GuestUser(userId), List.of(role.orElse(UserRole.GUEST)));
        } else {
            authentication = new UserAuthenticationToken(
                    new AuthUser(userId, role.orElse(UserRole.USER)), List.of(role.orElse(UserRole.USER)));
        }
        return authentication;
    }

    public static Authentication getAuthentication(final String userId, final UserRole role) {
        return getAuthentication(userId, Optional.of(role));
    }

    private static final String[] GUEST_PREFIXES = {"010", "GUEST"};
    private static boolean hasGuestPrefix(String userId) {
        for (String prefix : GUEST_PREFIXES) {
            if (userId.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }
}
