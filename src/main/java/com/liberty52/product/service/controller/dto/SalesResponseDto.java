package com.liberty52.product.service.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SalesResponseDto {
    private Long salesMoney;
    private Long salesQuantity;
}
