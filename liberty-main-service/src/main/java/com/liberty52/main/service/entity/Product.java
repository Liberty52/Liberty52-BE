package com.liberty52.main.service.entity;

import com.liberty52.main.service.entity.license.LicenseOption;
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
    private final String id = UUID.randomUUID().toString();

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductState productState;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private boolean isCustom;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private final List<ProductOption> productOptions = new ArrayList<>();

    @OneToOne(mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private LicenseOption licenseOption;

    private String pictureUrl;
    @Column(length = 10000)
    private String content = "";

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "product")
    private ProductDeliveryOption deliveryOption;

    @Builder
    private Product(String name, ProductState productState, Long price, boolean isCustom, String pictureUrl) {
        this.name = name;
        this.productState = productState;
        this.price = price;
        this.isCustom = isCustom;
        this.pictureUrl = pictureUrl;
    }

    public static Product create(String name, ProductState state, Long price, boolean isCustom) {
        return builder().name(name)
                .productState(state)
                .price(price)
                .isCustom(isCustom)
                .build();
    }

    public void setProductPictureUrl(String pictureUrl) {
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

    public void addLicenseOption(LicenseOption licenseOption) {
        this.licenseOption = licenseOption;
    }

    public void setDeliveryOption(ProductDeliveryOption deliveryOption) {
        this.deliveryOption = deliveryOption;
    }

    public float getRate(List<Review> productReviewList) {
        if (productReviewList.size() > 0) {
            int totalRate = productReviewList.stream().mapToInt(Review::getRating).sum();
            return totalRate / productReviewList.size();
        } else {
            return 0;
        }
    }
}
