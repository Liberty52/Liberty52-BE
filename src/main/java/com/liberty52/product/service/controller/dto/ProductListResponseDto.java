package com.liberty52.product.service.controller.dto;

import com.liberty52.product.service.entity.Product;
import com.liberty52.product.service.entity.Review;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class ProductListResponseDto extends PageDtoBase<ProductListResponseDto.ProductInfo> {

    public ProductListResponseDto(Page<Product> page, List<Review> reviewList) {
        super(page, page.getContent().stream().map(p -> new ProductInfo(p, reviewList)).toList());
    }

    @Data
    public static class ProductInfo {
        private String id;
        private String name;
        private String state;
        private Long price;
        private float meanRate;
        private String pictureUrl;
        private boolean isCustom;

        public ProductInfo(Product p, List<Review> reviewList) {
            List<Review> productReviewList = reviewList.stream().filter(r -> r.getCustomProduct().getProduct().equals(p)).collect(Collectors.toList());
            this.id = p.getId();
            this.name = p.getName();
            this.state = p.getProductState().name();
            this.price = p.getPrice();
            this.meanRate = p.getRate(productReviewList);
            this.pictureUrl = p.getPictureUrl();
            this.isCustom = p.isCustom();

        }
    }
}
