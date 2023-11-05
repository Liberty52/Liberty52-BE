package com.liberty52.product.service.controller.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class CartItemResponse {

    String id;

    String name;

    String imageUrl;

    long price;

    int quantity;

    List<CartOptionResponse> options;

    String courierName;

    int deliveryFee;

    public static CartItemResponse of(String id, String name, String imageUrl, long price, int quantity,  List<CartOptionResponse> options, String courierName, int deliveryFee){
        return new CartItemResponse(id, name, imageUrl, price, quantity, options, courierName, deliveryFee);
    }

}
