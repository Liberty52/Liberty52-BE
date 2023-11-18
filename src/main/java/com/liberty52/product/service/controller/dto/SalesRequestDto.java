package com.liberty52.product.service.controller.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SalesRequestDto {
    String productName;
    LocalDate startDate;
    LocalDate endDate;
}
