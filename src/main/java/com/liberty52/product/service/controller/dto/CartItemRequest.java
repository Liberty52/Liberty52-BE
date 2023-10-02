package com.liberty52.product.service.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CartItemRequest {

    @NotBlank
    String productName;

    @NotNull
    Integer quantity;

    @NotNull
    String[] optionIds;

    public CartItemRequest() {

    }


    public void create(String productName, Integer quantity, String[] optionIds) {
        this.productName = productName;
        this.quantity = quantity;
        this.optionIds = optionIds;
    }

    @Builder
    private CartItemRequest(String productName, Integer quantity, String[] optionIds) {
        this.productName = productName;
        this.quantity = quantity;
        this.optionIds = optionIds;
    }

}
