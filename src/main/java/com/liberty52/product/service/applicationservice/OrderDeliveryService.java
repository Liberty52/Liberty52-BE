package com.liberty52.product.service.applicationservice;

import com.liberty52.product.service.controller.dto.AdminAddOrderDeliveryDto;
import com.liberty52.product.service.controller.dto.AdminCourierListDto;

public interface OrderDeliveryService {
    AdminCourierListDto.Response getCourierCompanyList(Boolean isInternational);

    AdminAddOrderDeliveryDto.Response add(String orderId, AdminAddOrderDeliveryDto.Request dto);

    String getRealTimeDeliveryInfoRedirectUrl(String authId, String orderId, String courierCode, String trackingNumber);

    String getGuestRealTimeDeliveryInfoRedirectUrl(String guestId, String orderNumber, String courierCode, String trackingNumber);
}
