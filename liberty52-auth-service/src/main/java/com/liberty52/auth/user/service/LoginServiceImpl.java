package com.liberty52.auth.user.service;

import com.liberty52.auth.global.exception.external.unauthorized.AuthNotFoundException;
import com.liberty52.auth.global.exception.external.unauthorized.AuthUnauthorizedException;
import com.liberty52.auth.global.exception.external.unauthorized.InvalidTokenException;
import com.liberty52.auth.global.jwt.JwtService;
import com.liberty52.auth.user.entity.Auth;
import com.liberty52.auth.user.repository.AuthRepository;
import com.liberty52.auth.user.web.dto.LoginRequestDto;
import com.liberty52.auth.user.web.dto.LoginResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

  private final AuthRepository authRepository;
  private final JwtService jwtService;
  private final PasswordEncoder encoder;


  @Override
  public LoginResponseDto login(LoginRequestDto dto, HttpServletResponse response) {
    Auth auth = authRepository.findByEmail(dto.getEmail()).orElseThrow(AuthNotFoundException::new);
    if (!encoder.matches(dto.getPassword(), auth.getPassword())) {
      throw new AuthUnauthorizedException();
    }
    String refreshToken = jwtService.createTokensAndAddHeaders(auth, dto.getIsAutoLogin(), response);
    if (refreshToken != null) {
      auth.updateRefreshToken(refreshToken);
    }
    return LoginResponseDto.of(auth.getName(), auth.getProfileUrl(), auth.getRole());
  }

  @Override
  public void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response,
      String reqRefreshToken) {
    Auth auth = authRepository.findByRefreshToken(reqRefreshToken)
        .orElseThrow(InvalidTokenException::new);
    String refreshToken = jwtService.createTokensAndAddHeaders(auth, true, response);
    auth.updateRefreshToken(refreshToken);
  }

}
