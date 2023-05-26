package com.liberty52.product.service.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OptionDetailModifyRequestDto {

    @NotBlank
    String name;

    @NotNull
    Integer price;

    @NotNull
    Boolean onSail;
}
