package com.liberty52.auth.user.service;

import com.liberty52.auth.user.web.dto.SignUpRequestDto;
import org.springframework.web.multipart.MultipartFile;

public interface EmailSignUpService {
    void signUp(SignUpRequestDto dto, MultipartFile imageFile);
}
