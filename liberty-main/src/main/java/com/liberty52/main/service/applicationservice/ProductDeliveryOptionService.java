package com.liberty52.main.service.applicationservice;

import com.liberty52.main.service.controller.dto.AdminProductDeliveryOptionsDto;

public interface ProductDeliveryOptionService {
    AdminProductDeliveryOptionsDto.Response create(
            String role,
            String productId,
            AdminProductDeliveryOptionsDto.Request dto
    );

    AdminProductDeliveryOptionsDto.Response getByProductIdForAdmin(
            String role,
            String productId
    );

    AdminProductDeliveryOptionsDto.Response update(
            String role,
            String productId,
            AdminProductDeliveryOptionsDto.Request dto
    );
}
