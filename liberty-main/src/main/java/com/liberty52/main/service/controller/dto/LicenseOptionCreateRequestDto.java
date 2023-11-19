package com.liberty52.main.service.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LicenseOptionCreateRequestDto {

    @NotBlank
    String name;

    public static LicenseOptionCreateRequestDto create(String name) {
        return new LicenseOptionCreateRequestDto(name);
    }
}
