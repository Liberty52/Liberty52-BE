package com.liberty52.product.service.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CartItemRequestWithLicense {

    @NotBlank
    String productId;

    @NotNull
    Integer quantity;

    String[] optionDetailIds;

    @NotNull
    String licenseOptionIds;
}
