package com.liberty52.product.service.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LicenseOptionCreateRequestDto {

    @NotBlank
    String name;
    @NotNull
    Boolean require;
    @NotNull
    Boolean onSale;

    public static LicenseOptionCreateRequestDto create(String name, Boolean require, Boolean onSale){
        return new LicenseOptionCreateRequestDto(name, require, onSale);
    }
}
