package com.liberty52.authentication.core.principal;

import com.liberty52.authentication.core.UserRole;

import java.util.Objects;

public class AnonymousUser extends AbstractUser {
    private AnonymousUser() {
        super("", UserRole.ANONYMOUS);
    }

    public AnonymousUser(String userId) {
        super(userId, UserRole.ANONYMOUS);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnonymousUser principal = (AnonymousUser) o;
        if (principal.getUserId() == null
                || principal.getUserId().isBlank()
                || principal.getRole() == null) {
            return false;
        }
        if (!this.getUserId().equals(principal.getUserId())
                || this.getRole() != principal.getRole()) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getUserId(), this.getRole());
    }

    @Override
    public String toString() {
        return "AnonymousUser {" +
                "userId='" + this.getUserId() + '\'' +
                ", role=" + this.getRole() +
                '}';
    }
}
