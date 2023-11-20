package com.liberty52.auth.global.exception.external.forbidden;

import com.liberty52.auth.service.entity.Role;

public class InvalidRoleException extends ForbiddenException {
    public InvalidRoleException(Role expected, String actual) {
        super(String.format("Role %s is required but the role is %s", expected.name(), actual));
    }
}
