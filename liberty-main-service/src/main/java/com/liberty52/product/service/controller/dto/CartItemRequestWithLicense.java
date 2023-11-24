package com.liberty52.product.service.controller.dto;

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
    String licenseOptionId;

    @Builder
    public CartItemRequestWithLicense(String productId, Integer quantity, String[] optionDetailIds, String licenseOptionId) {
        this.productId = productId;
        this.quantity = quantity;
        this.optionDetailIds = optionDetailIds;
        this.licenseOptionId = licenseOptionId;
    }

    public CartItemRequestWithLicense() {

    }
}
