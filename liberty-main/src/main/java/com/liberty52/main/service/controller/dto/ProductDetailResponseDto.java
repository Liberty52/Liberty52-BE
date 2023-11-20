package com.liberty52.main.service.controller.dto;

import com.liberty52.main.service.entity.OptionDetail;
import com.liberty52.main.service.entity.Product;
import com.liberty52.main.service.entity.ProductOption;
import lombok.Data;

import java.util.List;

@Data
public class ProductDetailResponseDto {
    private String id;
    private String name;
    private String pictureUrl;
    private String state;
    private Long price;
    private boolean isCustom;
    private String content;
    private List<ProductOptionDto> options;
    private String courierName;
    private int deliveryFee;

    public ProductDetailResponseDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.pictureUrl = product.getPictureUrl();
        this.state = product.getProductState().name();
        this.price = product.getPrice();
        this.isCustom = product.isCustom();
        this.content = product.getContent();
        this.options = product.getProductOptions().stream().filter(ProductOption::isOnSale)
                .map(ProductOptionDto::new).toList();
        if (product.getDeliveryOption() != null) {
            this.courierName = product.getDeliveryOption().getCourierName();
            this.deliveryFee = product.getDeliveryOption().getFee();
        }
    }

    @Data
    public static class ProductOptionDto {
        private String id;
        private String name;
        private boolean require;
        private List<OptionDetailDto> optionItems;

        public ProductOptionDto(ProductOption op) {
            this.id = op.getId();
            this.name = op.getName();
            this.require = op.isRequire();
            this.optionItems = op.getOptionDetails().stream()
                    .filter(OptionDetail::isOnSale)
                    .map(OptionDetailDto::new).toList();
        }

        @Data
        public static class OptionDetailDto {
            private String id;
            private String name;
            private Integer price;
            private boolean isOnSale;
            private Integer stock;

            public OptionDetailDto(OptionDetail detail) {
                this.id = detail.getId();
                this.name = detail.getName();
                this.price = detail.getPrice();
                this.isOnSale = detail.isOnSale();
                this.stock = detail.getStock();
            }
        }
    }
}
