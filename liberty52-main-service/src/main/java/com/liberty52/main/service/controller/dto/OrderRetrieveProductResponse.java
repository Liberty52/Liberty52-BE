package com.liberty52.main.service.controller.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class OrderRetrieveProductResponse {

    private String customProductId;
    private String name;
    private int quantity;
    private Long price;

    private String productUrl;

    private List<String> options;
    private boolean hasReview;

    private boolean isCustom;
    private String licenseArtUrl;
    private String licenseArtName;
    private String licenseArtistName;

    @QueryProjection
    public OrderRetrieveProductResponse(String name, int quantity, Long price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    @QueryProjection
    public OrderRetrieveProductResponse(String customProductId, String name, int quantity, Long price,
        String productUrl, boolean hasReview, List<String> options, boolean isCustom,
        String licenseArtUrl, String licenseArtName, String licenseArtistName) {
        this.customProductId = customProductId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.productUrl = productUrl;
        this.options = options;
        this.hasReview = hasReview;
        this.isCustom = isCustom;
        this.licenseArtUrl = licenseArtUrl;
        this.licenseArtName = licenseArtName;
        this.licenseArtistName = licenseArtistName;
    }
}
