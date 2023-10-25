package com.liberty52.product.service.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id
    private String id = UUID.randomUUID().toString();

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductState productState;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private boolean isCustom;

    @OneToMany(mappedBy = "product")
    private List<ProductOption> productOptions = new ArrayList<>();

    private String pictureUrl;
    private String content;

    @Builder
    private Product(String name, ProductState productState, Long price, boolean isCustom, String pictureUrl) {
        this.name = name;
        this.productState = productState;
        this.price = price;
        this.isCustom = isCustom;
        this.pictureUrl = pictureUrl;
    }

    public void createContent(String content) {
        this.content = content;
    }

    public void deleteContent() {
        this.content = "";
    }

    public void addOption(ProductOption productOption) {
        this.productOptions.add(productOption);
    }
    public static Product create(String name, ProductState state, Long price, boolean isCustom) {
        return builder().name(name)
                .productState(state)
                .price(price)
                .isCustom(isCustom)
                .build();
    }

    public float getRate(List<Review> productReviewList) {
        if(productReviewList.size() > 0) {
            int totalRate = productReviewList.stream().mapToInt(Review::getRating).sum();
            return totalRate / productReviewList.size();
        } else {
            return 0;
        }
    }
}
