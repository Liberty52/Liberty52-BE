package com.liberty52.product.service.applicationservice;

import com.liberty52.product.service.controller.dto.AdminCourierListDto;

public interface OrderDeliveryService {
    AdminCourierListDto.Response getCourierCompanyList(Boolean isInternational);
}