package com.liberty52.main.service.controller.dto;

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

<<<<<<< HEAD:src/main/java/com/liberty52/product/service/controller/dto/CartItemResponse.java
    boolean isCustom;

    public static CartItemResponse of(String id, String name, String imageUrl, long price, int quantity,  List<CartOptionResponse> options, String courierName, int deliveryFee, boolean isCustom){
        return new CartItemResponse(id, name, imageUrl, price, quantity, options, courierName, deliveryFee, isCustom);
=======
    public static CartItemResponse of(String id, String name, String imageUrl, long price, int quantity, List<CartOptionResponse> options, String courierName, int deliveryFee) {
        return new CartItemResponse(id, name, imageUrl, price, quantity, options, courierName, deliveryFee);
>>>>>>> 73d134bb762e656040fe486b3e181afef0c410e4:liberty-main/src/main/java/com/liberty52/main/service/controller/dto/CartItemResponse.java
    }

}
