package com.liberty52.main.service.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class CartModifyWithLicenseRequestDto {

    @NotNull
    private String licenseId;

    public static CartModifyWithLicenseRequestDto create(String licenseId) {
        return new CartModifyWithLicenseRequestDto(licenseId);
    }
}
