package com.liberty52.auth.user.service;

import com.liberty52.auth.user.web.dto.LoginRequestDto;
import com.liberty52.auth.user.web.dto.LoginResponseDto;
import jakarta.servlet.http.HttpServletResponse;


public interface LoginService {
    LoginResponseDto login(LoginRequestDto dto, HttpServletResponse response);

    void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken);
}
