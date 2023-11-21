package com.liberty52.auth.global.utils;

import com.liberty52.auth.global.exception.external.forbidden.InvalidAdminRoleException;
import com.liberty52.auth.user.entity.Role;

public class AdminRoleUtils {
    public static void checkRole(String role) {
        if (!Role.ADMIN.name().equals(role)) {
            throw new InvalidAdminRoleException(role);
        }
    }
}