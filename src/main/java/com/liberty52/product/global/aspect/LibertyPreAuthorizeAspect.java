package com.liberty52.product.global.aspect;

import com.liberty.authentication.annotation.LibertyPreAuthorize;
import com.liberty.authentication.core.Authentication;
import com.liberty.authentication.core.UserRole;
import com.liberty.authentication.core.context.AuthenticationContextHolder;
import com.liberty52.product.global.exception.external.forbidden.ForbiddenException;
import com.liberty52.product.global.exception.external.unauthorized.UnauthorizedException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Aspect
@Component
public class LibertyPreAuthorizeAspect {

    @Before("@annotation(com.liberty.authentication.annotation.LibertyPreAuthorize)")
    public void invoke(final JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LibertyPreAuthorize preAuthorize = method.getAnnotation(LibertyPreAuthorize.class);

        Authentication authentication = AuthenticationContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("You are an unauthorized user.");
        }

        if (!hasAnyRoles(preAuthorize, authentication.getAuthorities())) {
            throw new ForbiddenException("You cannot access this API.");
        }
    }

    private Set<UserRole> getPreAuthoritySet(LibertyPreAuthorize preAuthorize) {
        Set<UserRole> roleSet = new HashSet<>(Arrays.asList(preAuthorize.roles()));
        roleSet.add(preAuthorize.role());
        roleSet.add(UserRole.ADMIN);
        return roleSet;
    }

    private boolean hasAnyRoles(LibertyPreAuthorize preAuthorize, Collection<UserRole> authorities) {
        Set<UserRole> roleSet = getPreAuthoritySet(preAuthorize);
        for (UserRole role : authorities) {
            if (roleSet.contains(role)) {
                return true;
            }
        }
        return false;
    }
}
