package com.liberty52.product.service.applicationservice.impl;

import com.liberty52.product.service.applicationservice.OrderDeliveryService;
import com.liberty52.product.service.controller.dto.AdminCourierListDto;
import org.springframework.stereotype.Service;

@Service
public class OrderDeliveryServiceImpl implements OrderDeliveryService {
    @Override
    public AdminCourierListDto.Response getCourierCompanyList(Boolean isInternational) {
        return null;
    }
}
