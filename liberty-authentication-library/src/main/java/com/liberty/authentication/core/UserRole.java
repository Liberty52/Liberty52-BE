package com.liberty.authentication.core;

public enum UserRole {
    ANONYMOUS,
    USER,
    ADMIN;

    public static UserRole from(String source) {
        if (source == null || source.isBlank()) {
            return ANONYMOUS;
        }
        if (ADMIN.name().equals(source) || ADMIN.name().toLowerCase().equals(source)
                || source.toUpperCase().contains(ADMIN.name())) {
            return ADMIN;
        }
        if (USER.name().equals(source) || USER.name().toLowerCase().equals(source)
                || source.toUpperCase().contains(USER.name())) {
            return USER;
        }
        return ANONYMOUS;
    }
}
