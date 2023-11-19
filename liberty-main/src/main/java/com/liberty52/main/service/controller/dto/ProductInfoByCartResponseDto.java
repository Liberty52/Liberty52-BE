package com.liberty52.main.service.controller.dto;

import com.liberty52.main.service.entity.Product;
import com.liberty52.main.service.entity.ProductOption;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfoByCartResponseDto {
    String productId;
    List<ProductOptionInfoByCartResponseDto> productOptionList;

    public ProductInfoByCartResponseDto(Product product) {
        productId = product.getId();
        productOptionList = product.getProductOptions().stream().filter(ProductOption::isOnSale).map(ProductOptionInfoByCartResponseDto::new).collect(Collectors.toList());
    }

}
