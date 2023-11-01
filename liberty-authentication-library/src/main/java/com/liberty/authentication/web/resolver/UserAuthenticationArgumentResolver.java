package com.liberty.authentication.web.resolver;

import com.liberty.authentication.annotation.UserAuthentication;
import com.liberty.authentication.core.Authentication;
import com.liberty.authentication.core.context.AuthenticationContextHolder;
import com.liberty.authentication.core.context.AuthenticationContextHolderStrategy;
import com.liberty.authentication.core.exception.AuthenticationError;
import com.liberty.authentication.core.exception.AuthenticationException;
import com.liberty.authentication.core.principal.User;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.annotation.Annotation;

public class UserAuthenticationArgumentResolver implements HandlerMethodArgumentResolver {

    private final AuthenticationContextHolderStrategy strategy = AuthenticationContextHolder.getContextHolderStrategy();

    @Override
    public boolean supportsParameter(@NonNull MethodParameter parameter) {
        return findMethodAnnotation(UserAuthentication.class, parameter) != null;
    }

    @Override
    public User resolveArgument(@NonNull MethodParameter parameter, ModelAndViewContainer mavContainer,
                                @NonNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        Authentication authentication = strategy.getContext().getAuthentication();
        if (authentication == null) {
            throw new AuthenticationException(AuthenticationError.UNAUTHORIZED);
        }

        User principal = authentication.getPrincipal();
        if (principal == null) {
            throw new AuthenticationException(AuthenticationError.UNAUTHORIZED);
        }

        return principal;
    }

    private <T extends Annotation> T findMethodAnnotation(
            @SuppressWarnings("SameParameterValue") Class<T> annotationClass,
            MethodParameter parameter
    ) {
        T annotation = parameter.getParameterAnnotation(annotationClass);
        if (annotation != null) {
            return annotation;
        }
        Annotation[] annotationsToSearch = parameter.getParameterAnnotations();
        for (Annotation toSearch : annotationsToSearch) {
            annotation = AnnotationUtils.findAnnotation(toSearch.annotationType(), annotationClass);
            if (annotation != null) {
                return annotation;
            }
        }
        return null;
    }
}
