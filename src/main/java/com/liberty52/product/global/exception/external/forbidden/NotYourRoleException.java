package com.liberty52.product.global.exception.external.forbidden;

public class NotYourRoleException extends NotYourResourceException {
    public NotYourRoleException(String role) {
        super("Role", role);
    }
}
