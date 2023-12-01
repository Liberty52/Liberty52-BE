package com.liberty52.auth.user.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PasswordUpdateRequestDto {

    private String emailToken;
    @NotBlank
    private String password;

}
