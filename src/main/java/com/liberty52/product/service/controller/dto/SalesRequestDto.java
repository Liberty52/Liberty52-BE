package com.liberty52.product.service.controller.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class SalesRequestDto {
    String productName;
    LocalDate startDate;
    LocalDate endDate;
}
