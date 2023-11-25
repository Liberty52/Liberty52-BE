package com.liberty52.main.service.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class CartModifyWithLicenseRequestDto {
    private List<String> optionDetailIds;

    @NotNull
    private String licenseId;

    @Min(1)
    private int quantity;

    public static CartModifyWithLicenseRequestDto create(List<String> optionDetailIds, String licenseId, int quantity) {
        return new CartModifyWithLicenseRequestDto(optionDetailIds, licenseId, quantity);
    }
}
