package com.liberty52.authentication.core;

public enum UserRole {
    ANONYMOUS,
    GUEST,
    USER,
    ADMIN;

    public static UserRole from(String source) {
        if (source == null || source.isBlank()) {
            return null;
        }
        if (ADMIN.name().equals(source) || ADMIN.name().toLowerCase().equals(source)
                || source.toUpperCase().contains(ADMIN.name())) {
            return ADMIN;
        }
        if (GUEST.name().equals(source) || GUEST.name().toLowerCase().equals(source)
                || source.toUpperCase().contains(GUEST.name())) {
            return GUEST;
        }
        if (USER.name().equals(source) || USER.name().toLowerCase().equals(source)
                || source.toUpperCase().contains(USER.name())) {
            return USER;
        }
        return ANONYMOUS;
    }
}
