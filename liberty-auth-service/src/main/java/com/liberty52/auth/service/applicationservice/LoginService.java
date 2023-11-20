package com.liberty52.auth.service.applicationservice;

import com.liberty52.auth.service.controller.dto.LoginRequestDto;
import com.liberty52.auth.service.controller.dto.LoginResponseDto;
import jakarta.servlet.http.HttpServletResponse;


public interface LoginService {
    LoginResponseDto login(LoginRequestDto dto, HttpServletResponse response);

    void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken);
}
