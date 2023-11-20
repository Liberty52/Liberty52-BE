package com.liberty52.auth.service.controller;

import com.liberty52.auth.service.applicationservice.EmailSignUpService;
import com.liberty52.auth.service.controller.dto.SignUpRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class EmailSignUpController {
    private final EmailSignUpService emailSignUpService;

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(
            @Validated @RequestPart("dto") SignUpRequestDto dto,
            @RequestPart(value = "file", required = false) MultipartFile imageFile
    ) {
        emailSignUpService.signUp(dto, imageFile);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public int some () {
        return 100;
    }
}
