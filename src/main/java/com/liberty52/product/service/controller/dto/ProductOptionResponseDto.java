package com.liberty52.product.service.controller.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionResponseDto {

    String optionName;
    boolean require;
    boolean onSail;
    List<ProductOptionDetailResponseDto> optionDetailList;
}
