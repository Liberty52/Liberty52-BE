package com.liberty52.auth.user.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class EmailDto {

    @NotBlank
    private String email;

}
