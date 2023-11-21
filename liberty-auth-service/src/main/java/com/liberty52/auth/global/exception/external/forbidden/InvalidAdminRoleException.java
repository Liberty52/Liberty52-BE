package com.liberty52.auth.global.exception.external.forbidden;

import com.liberty52.auth.user.entity.Role;

public class InvalidAdminRoleException extends InvalidRoleException {
    public InvalidAdminRoleException(String actual) {
        super(Role.ADMIN, actual);
    }
}
