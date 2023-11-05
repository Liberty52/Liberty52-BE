package com.liberty52.product.service.utils;

import com.liberty52.authentication.core.UserRole;
import com.liberty52.authentication.core.principal.AuthUser;
import com.liberty52.authentication.core.principal.GuestUser;
import com.liberty52.authentication.core.principal.User;

public class MockUser {
    private MockUser() {}

    public static User guest() {
        return guest("TEST-GUEST");
    }

    public static User guest(String guestId) {
        return new GuestUser(guestId);
    }

    public static User user() {
        return user("1");
    }

    public static User user(String userId) {
        return new AuthUser(userId, UserRole.USER);
    }

    public static User admin() {
        return admin("1");
    }

    public static User admin(String adminId) {
        return new AuthUser(adminId, UserRole.ADMIN);
    }
}
