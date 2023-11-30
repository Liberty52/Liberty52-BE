package com.liberty52.main.service.controller.dto;

import com.liberty52.main.service.entity.ProductState;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class ProductInfoRetrieveResponseDto {

    String id;
    String pictureUrl;
    String name;
    long price;
    float meanRating;
    int ratingCount;
    boolean isCustom;
    String content;
    private ProductState state;
    private int order;

    public static ProductInfoRetrieveResponseDto of(String id, String pictureUrl, String name, long price,
        float meanRating, int ratingCount, boolean isCustom, String content, ProductState state, int order) {
        return new ProductInfoRetrieveResponseDto(id, pictureUrl, name, price, meanRating, ratingCount, isCustom,
            content, state, order);
    }

}
