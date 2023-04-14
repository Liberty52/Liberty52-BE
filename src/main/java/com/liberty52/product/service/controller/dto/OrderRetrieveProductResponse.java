package com.liberty52.product.service.controller.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class OrderRetrieveProductResponse {

    private String name;
    private int quantity;
    private Long price;


    @QueryProjection
    public OrderRetrieveProductResponse(String name, int quantity, Long price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
}
