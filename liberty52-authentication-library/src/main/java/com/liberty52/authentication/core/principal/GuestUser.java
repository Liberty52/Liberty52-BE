package com.liberty52.authentication.core.principal;

import com.liberty52.authentication.core.UserRole;

import java.util.Objects;

public class GuestUser extends AbstractUser {

    private GuestUser() {
        super("", UserRole.GUEST);
    }

    public GuestUser(String userId) {
        super(userId, UserRole.GUEST);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GuestUser principal = (GuestUser) o;
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
        return "GuestUser {" +
                "userId='" + this.getUserId() + '\'' +
                ", role=" + this.getRole() +
                '}';
    }
}
