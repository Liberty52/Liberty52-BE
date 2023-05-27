package com.liberty52.product.service.applicationservice;

import com.liberty52.product.service.controller.dto.DeliveryOptionDto;

public interface DeliveryOptionModifyService {
    DeliveryOptionDto updateDefaultDeliveryFee(String role, int fee);
}
