package com.liberty52.main.service.controller.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SalesResponseDto {
    private Long totalSalesMoney;
    private Long totalSalesQuantity;
    private Map<String, Map<String, Object>> monthlySales = new HashMap<>();

    @Builder
    public SalesResponseDto(Long totalSalesMoney, Long totalSalesQuantity, Map<String, Map<String, Object>> monthlySales) {
        this.totalSalesMoney = totalSalesMoney;
        this.totalSalesQuantity = totalSalesQuantity;
        this.monthlySales = monthlySales;
    }

}
