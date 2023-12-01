package com.liberty52.main.service.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CartItemRequestWithLicense {

    @NotBlank
    String productId;

    @NotNull
    Integer quantity;

    String[] optionDetailIds;

    @NotNull
    String licenseOptionDetailId;

    @Builder
    public CartItemRequestWithLicense(String productId, Integer quantity, String[] optionDetailIds, String licenseOptionDetailId) {
        this.productId = productId;
        this.quantity = quantity;
        this.optionDetailIds = optionDetailIds;
        this.licenseOptionDetailId = licenseOptionDetailId;
    }

    public CartItemRequestWithLicense() {

    }
}
