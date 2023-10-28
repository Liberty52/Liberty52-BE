package com.liberty52.product.service.controller.dto;

import com.liberty52.product.service.entity.ProductDeliveryOption;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public class AdminProductDeliveryOptionsDto {
    @Builder
    public record Request(
            @NotNull(message = "택배사 이름을 입력해주세요")
            @NotBlank(message = "택배사 이름을 입력해주세요")
            String courierName,
            @NotNull(message = "배송비를 입력해주세요")
            @Min(value = 0, message = "배송비는 0 이상 입력 가능합니다")
            Integer fee
    ) {}

    @Builder
    public record Response(
            String productId,
            String courierName,
            Integer fee
    ) {
        public static Response from(ProductDeliveryOption entity) {
            return Response.builder()
                    .productId(entity.getProduct().getId())
                    .courierName(entity.getCourierName())
                    .fee(entity.getFee())
                    .build();
        }
    }
}
