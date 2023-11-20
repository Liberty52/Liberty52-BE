package com.liberty52.auth.service.applicationservice;

import com.liberty52.auth.service.controller.dto.SignUpRequestDto;
import org.springframework.web.multipart.MultipartFile;

public interface EmailSignUpService {
    void signUp(SignUpRequestDto dto, MultipartFile imageFile);
}
