package com.liberty52.product.service.applicationservice;

import com.liberty52.product.service.controller.dto.OrderCancelRequestDto;

public interface OrderCancelService {

    void cancelOrder(String authId, OrderCancelRequestDto dto);

}
