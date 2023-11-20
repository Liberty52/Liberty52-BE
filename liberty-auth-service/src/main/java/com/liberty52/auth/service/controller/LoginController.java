package com.liberty52.auth.service.controller;

import com.liberty52.auth.service.applicationservice.LoginService;
import com.liberty52.auth.service.controller.dto.LoginRequestDto;
import com.liberty52.auth.service.controller.dto.LoginResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public LoginResponseDto login(@Validated @RequestBody LoginRequestDto dto, HttpServletResponse response) {
        return loginService.login(dto, response);
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @GetMapping("/refresh")
    public void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, @RequestHeader("LB-RefreshToken") String refreshToken){
        loginService.checkRefreshTokenAndReIssueAccessToken(response,refreshToken);
    }
}
