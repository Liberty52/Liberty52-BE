package com.liberty52.authentication.web.filter;

import com.liberty52.authentication.core.Authentication;
import com.liberty52.authentication.core.UserRole;
import com.liberty52.authentication.core.context.AuthenticationContextHolder;
import com.liberty52.authentication.core.token.AuthenticationTokenUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;

import java.io.IOException;

public class AuthenticationFilter implements Filter {
    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {
        try {
            String userId = getUserId((HttpServletRequest) request);
            UserRole role = getRole((HttpServletRequest) request);

            Authentication authentication = AuthenticationTokenUtils.getAuthentication(userId, role);
            AuthenticationContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request, response);
        } finally {
            AuthenticationContextHolder.clearContext();
        }
    }

    private String getUserId(HttpServletRequest request) {
        return request.getHeader(HttpHeaders.AUTHORIZATION);
    }

    private UserRole getRole(HttpServletRequest request) {
        return UserRole.from(request.getHeader("LB-Role"));
    }

}
