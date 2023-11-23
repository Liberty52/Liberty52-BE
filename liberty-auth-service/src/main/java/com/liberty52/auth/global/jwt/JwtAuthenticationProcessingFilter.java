package com.liberty52.auth.global.jwt;

import com.liberty52.auth.user.entity.Auth;
import com.liberty52.auth.user.repository.AuthRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {

  private static final String NO_CHECK_URL = "/login"; // "/login"으로 들어오는 요청은 Filter 작동 X

  private final JwtService jwtService;
  private final AuthRepository authRepository;

  private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    if (request.getRequestURI().equals(NO_CHECK_URL)) {
      filterChain.doFilter(request, response); // "/login" 요청이 들어오면, 다음 필터 호출
      return;
    }

    String refreshToken = jwtService.extractRefreshToken(request)
        .filter(jwtService::isTokenValid)
        .orElse(null);

    if (refreshToken != null) {
      checkRefreshTokenAndReIssueAccessToken(response, refreshToken);
    }else {
      filterChain.doFilter(request, response); // TODO Filter 내에서 Exception 발생 시 Handling Filter 생성해야 함.
    }
  }

  public void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {
    authRepository.findByRefreshToken(refreshToken)
        .ifPresent(user -> {
          String reIssuedRefreshToken = reIssueRefreshToken(user);
          jwtService.sendAccessAndRefreshToken(response, jwtService.createAccessToken(user.getId(), user.getRole()),
              reIssuedRefreshToken);
        });
  }

  private String reIssueRefreshToken(Auth user) {
    String reIssuedRefreshToken = jwtService.createRefreshToken();
    user.updateRefreshToken(reIssuedRefreshToken);
    authRepository.saveAndFlush(user);
    return reIssuedRefreshToken;
  }
}
