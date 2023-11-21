package com.liberty52.main.service.controller.dto;

import com.liberty52.main.service.entity.OptionDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionDetailResponseDto {

    String optionDetailId;
    String optionDetailName;
    int price;
    boolean onSale;
    Integer stock;

    public ProductOptionDetailResponseDto(OptionDetail optionDetail) {
        optionDetailId = optionDetail.getId();
        optionDetailName = optionDetail.getName();
        price = optionDetail.getPrice();
        onSale = optionDetail.isOnSale();
        stock = optionDetail.getStock();
    }

}
