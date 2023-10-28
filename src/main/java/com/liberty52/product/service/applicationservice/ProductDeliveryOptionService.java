package com.liberty52.product.service.applicationservice;

import com.liberty52.product.service.controller.dto.AdminProductDeliveryOptionsDto;

public interface ProductDeliveryOptionService {
    AdminProductDeliveryOptionsDto.Response create(
            String role,
            String productId,
            AdminProductDeliveryOptionsDto.Request dto
    );

    AdminProductDeliveryOptionsDto.Response getByProductId(
            String role,
            String productId
    );

    AdminProductDeliveryOptionsDto.Response update(
            String role,
            String productId,
            AdminProductDeliveryOptionsDto.Request dto
    );
}
