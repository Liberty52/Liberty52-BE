package com.liberty52.product.service.controller.dto;

import com.liberty52.product.global.annotation.validation.NullableNotBlank;
import com.liberty52.product.global.annotation.validation.NullableNotNegative;
import com.liberty52.product.service.entity.ProductState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductModifyRequestDto{
    @NullableNotBlank(fieldname="name")
    private String name;

    private ProductState productState;

    @NullableNotNegative(fieldname="price")
    private Long price;

    private Boolean isCustom;
}
