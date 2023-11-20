package com.liberty52.main.service.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LicenseOptionModifyRequestDto {

    @NotBlank
    String name;

    public static LicenseOptionModifyRequestDto create(String name) {
        return new LicenseOptionModifyRequestDto(name);
    }
}
