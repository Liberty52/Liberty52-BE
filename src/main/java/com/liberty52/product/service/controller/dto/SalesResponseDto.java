package com.liberty52.product.service.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SalesResponseDto {
    private Long salesMoney;
    private Long salesQuantity;

    @Builder
    public SalesResponseDto(Long salesMoney, Long salesQuantity) {
        this.salesMoney = salesMoney;
        this.salesQuantity = salesQuantity;
    }

}
