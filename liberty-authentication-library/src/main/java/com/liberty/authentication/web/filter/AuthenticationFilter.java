package com.liberty.authentication.web.filter;

import com.liberty.authentication.core.Authentication;
import com.liberty.authentication.core.principal.AnonymousUser;
import com.liberty.authentication.core.principal.AuthUser;
import com.liberty.authentication.core.UserRole;
import com.liberty.authentication.core.context.AuthenticationContextHolder;
import com.liberty.authentication.core.principal.GuestUser;
import com.liberty.authentication.core.token.AnonymousAuthenticationToken;
import com.liberty.authentication.core.token.UserAuthenticationToken;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.util.List;

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

            Authentication authentication = getAuthentication(userId, role);
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

    private Authentication getAuthentication(String userId, UserRole role) {
        Authentication authentication;
        if (userId == null || userId.isBlank() || UserRole.ANONYMOUS.equals(role)) {
            authentication = new AnonymousAuthenticationToken(new AnonymousUser("anonymous"), List.of(UserRole.ANONYMOUS));
        } else if (UserRole.GUEST.equals(role)) {
            authentication = new UserAuthenticationToken(new GuestUser(userId), List.of(role));
        } else {
            authentication = new UserAuthenticationToken(new AuthUser(userId, role), List.of(role));
        }
        return authentication;
    }
}
