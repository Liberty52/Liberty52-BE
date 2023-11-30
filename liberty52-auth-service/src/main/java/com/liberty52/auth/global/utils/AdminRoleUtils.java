package com.liberty52.auth.global.utils;

import com.liberty52.auth.global.exception.external.forbidden.InvalidAdminRoleException;

public class AdminRoleUtils {
    public static final String ADMIN = "ADMIN";

    private AdminRoleUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static void isAdmin(String role) {
        if (!ADMIN.equals(role)) {
            throw new InvalidAdminRoleException(role);
        }
    }

}
