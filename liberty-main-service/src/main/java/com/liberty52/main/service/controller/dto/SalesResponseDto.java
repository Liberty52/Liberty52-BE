package com.liberty52.main.service.controller.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
