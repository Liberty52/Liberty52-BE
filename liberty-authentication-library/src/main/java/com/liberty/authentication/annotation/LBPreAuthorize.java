package com.liberty.authentication.annotation;

import com.liberty.authentication.core.UserRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LBPreAuthorize {
    UserRole role() default UserRole.ADMIN;
    UserRole[] roles() default { UserRole.ADMIN };
}
