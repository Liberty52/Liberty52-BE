package com.liberty52.main.service.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductOptionRequestDto {

    @NotBlank
    String name;

    @NotNull
    Boolean require;

    @NotNull
    Boolean onSale;

    public static CreateProductOptionRequestDto create(String name, boolean require, boolean onSale) {
        return new CreateProductOptionRequestDto(name, require, onSale);
    }
}
