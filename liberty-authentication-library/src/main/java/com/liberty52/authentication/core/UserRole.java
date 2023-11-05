package com.liberty52.authentication.core;

import java.util.Optional;

public enum UserRole {
    ANONYMOUS,
    GUEST,
    USER,
    ADMIN;

    public static Optional<UserRole> from(String source) {
        if (source == null || source.isBlank()) {
            return Optional.empty();
        }
        if (ADMIN.name().equals(source) || ADMIN.name().toLowerCase().equals(source)
                || source.toUpperCase().contains(ADMIN.name())) {
            return Optional.of(ADMIN);
        }
        if (GUEST.name().equals(source) || GUEST.name().toLowerCase().equals(source)
                || source.toUpperCase().contains(GUEST.name())) {
            return Optional.of(GUEST);
        }
        if (USER.name().equals(source) || USER.name().toLowerCase().equals(source)
                || source.toUpperCase().contains(USER.name())) {
            return Optional.of(USER);
        }
        return Optional.of(ANONYMOUS);
    }
}
