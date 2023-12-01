package com.liberty52.main.service.applicationservice;

import com.liberty52.main.service.controller.dto.VBankStatusModifyDto;
import com.liberty52.main.service.entity.OrderStatus;

public interface OrderStatusModifyService {

    void modifyOrderStatusByAdmin(String role, String orderId, OrderStatus status);

    void modifyOrderStatusOfVBankByAdmin(String role, String orderId, VBankStatusModifyDto dto);
}
