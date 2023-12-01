package com.liberty52.main.service.applicationservice;

import com.liberty52.authentication.core.principal.User;
import com.liberty52.main.service.controller.dto.AdminAddOrderDeliveryDto;
import com.liberty52.main.service.controller.dto.AdminCourierListDto;

public interface OrderDeliveryService {
    AdminCourierListDto.Response getCourierCompanyList(Boolean isInternational);

    AdminAddOrderDeliveryDto.Response add(String orderId, AdminAddOrderDeliveryDto.Request dto);

    String getRealTimeDeliveryInfoRedirectUrl(User user, String orderId, String courierCode, String trackingNumber);

    String getGuestRealTimeDeliveryInfoRedirectUrl(User guest, String orderNumber, String courierCode, String trackingNumber);
}
