package com.liberty52.main.service.controller.dto;

import com.liberty52.main.service.entity.OptionDetail;
import com.liberty52.main.service.entity.ProductOption;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionInfoByCartResponseDto {
    String optionId;
    String optionName;
    boolean require;
    List<OptionDetailInfoByCartResponseDto> optionDetailList;


    public ProductOptionInfoByCartResponseDto(ProductOption productOption) {
        optionId = productOption.getId();
        optionName = productOption.getName();
        require = productOption.isRequire();
        optionDetailList = productOption.getOptionDetails().stream().filter(OptionDetail::isOnSale).map(OptionDetailInfoByCartResponseDto::new).toList();
    }
}
