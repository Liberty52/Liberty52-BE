package com.liberty52.auth.service.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PasswordUpdateRequestDto {

    private String emailToken;
    @NotBlank
    private String password;

}
