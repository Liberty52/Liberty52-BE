package com.liberty52.main.service.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public class AdminAddOrderDeliveryDto {
    @Builder
    public record Request(
            @NotNull(message = "택배사 코드를 입력해주세요")
            @NotBlank(message = "택배사 코드를 입력해주세요")
            String courierCompanyCode,
            @NotNull(message = "택배사 이름을 입력해주세요")
            @NotBlank(message = "택배사 이름을 입력해주세요")
            String courierCompanyName,
            @NotNull(message = "운송장번호를 입력해주세요")
            @NotBlank(message = "운송장번호를 입력해주세요")
            String trackingNumber
    ) {
    }

    @Builder
    public record Response(
            String orderId,
            String courierCompanyCode,
            String courierCompanyName,
            String trackingNumber
    ) {
    }
}
