package com.liberty52.main.service.controller.dto;

import com.liberty52.main.service.entity.OrderDelivery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDeliveryDto {

    private String id;
    private String code;
    private String name;
    private String trackingNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static OrderDeliveryDto of(OrderDelivery entity) {
        if (entity == null) {
            return null;
        }

        return builder()
                .id(entity.getId())
                .code(entity.getCourierCompanyCode())
                .name(entity.getCourierCompanyName())
                .trackingNumber(entity.getTrackingNumber())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

}
