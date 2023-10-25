package com.liberty52.product.service.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CartItemRequest {

    @NotBlank
    String productId;

    @NotNull
    Integer quantity;

    @NotNull
    String[] optionDetailIds;

    public CartItemRequest() {

    }


    public void create(String productId, Integer quantity, String[] optionDetailIds) {
        this.productId = productId;
        this.quantity = quantity;
        this.optionDetailIds = optionDetailIds;
    }

    @Builder
    private CartItemRequest(String productId, Integer quantity, String[] optionDetailIds) {
        this.productId = productId;
        this.quantity = quantity;
        this.optionDetailIds = optionDetailIds;
    }

}
