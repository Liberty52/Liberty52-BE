package com.liberty52.main.service.controller.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SalesResponseDto {
    private Long totalSalesMoney;
    private Long totalSalesQuantity;
    private List<MonthlySales> monthlySales = new ArrayList<>();

    @Builder
    public SalesResponseDto(Long totalSalesMoney, Long totalSalesQuantity, List<MonthlySales> monthlySales) {
        this.totalSalesMoney = totalSalesMoney;
        this.totalSalesQuantity = totalSalesQuantity;
        this.monthlySales = monthlySales;
    }

    @Setter
    @Getter
    public static class MonthlySales {
        private String year;
        private String month;
        private Long salesMoney;
        private Integer salesQuantity;
    }
}
