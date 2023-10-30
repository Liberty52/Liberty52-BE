package com.liberty.authentication.core;

import java.util.Objects;

public record UserPrincipal(
        String userId,
        UserRole role
) {
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof UserPrincipal principal)) {
            return false;
        }
        if (principal.userId == null || principal.userId.isBlank() || principal.role == null) {
            return false;
        }
        if (!this.userId.equals(principal.userId) || this.role != principal.role) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UserPrincipal{" +
                "userId='" + userId + '\'' +
                ", role=" + role +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, role);
    }
}
