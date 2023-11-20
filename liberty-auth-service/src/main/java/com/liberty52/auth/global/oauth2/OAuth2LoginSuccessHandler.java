package com.liberty52.auth.global.oauth2;

import com.liberty52.auth.global.jwt.JwtService;
import com.liberty52.auth.global.jwt.Tokens;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
//@Transactional
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

  private final JwtService jwtService;
  @Value("${frontend.redirectUrlWithAccessTokenAndRefreshToken}")
  private String redirectedFrontURLWithTokens;

  @Value("${jwt.config.type.prefix}")
  private String authorizationTypePrefix;
  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
    log.debug("OAuth2 Login 성공!");
    CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
    Tokens tokens = loginSuccess(response, oAuth2User);// 로그인에 성공한 경우 access, refresh 토큰 생성
    response.sendRedirect(String.format(redirectedFrontURLWithTokens,tokens.getAccessToken(),tokens.getRefreshToken()));
  }
  private Tokens loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User){
    String accessToken = jwtService.createAccessToken(oAuth2User.getId(), oAuth2User.getRole());
    String refreshToken = jwtService.createRefreshToken();

    jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
    jwtService.updateRefreshToken(oAuth2User.getEmail(), refreshToken);

    return new Tokens(authorizationTypePrefix + accessToken, refreshToken);
  }
}

