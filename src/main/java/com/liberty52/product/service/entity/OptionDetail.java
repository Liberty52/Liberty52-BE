package com.liberty52.product.service.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "option_detail")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OptionDetail {
    @Id
    private String id = UUID.randomUUID().toString();

    @ManyToOne
    @JoinColumn(name = "product_option_id")
    private ProductOption productOption;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private boolean onSale;

    @Column(nullable = false)
    private Integer stock;

    @Builder
    private OptionDetail(String name, Integer price, boolean onSale, Integer stock) {
        this.name = name;
        this.price = price;
        this.onSale = onSale;
        this.stock = stock;
    }

    public static OptionDetail create(String name, Integer price, boolean onSale, Integer stock) {
        return builder().name(name).price(price).onSale(onSale).stock(stock).build();
    }

    public void associate(ProductOption productOption) {
        this.productOption = productOption;
        this.productOption.addDetail(this);
    }

    public void updateOnSale() {
        onSale = !onSale;
    }

    public void modify(String name, Integer price, Boolean onSail, Integer stock) {
        this.name = name;
        this.price = price;
        this.onSale = onSail;
        this.stock = stock;
    }
}