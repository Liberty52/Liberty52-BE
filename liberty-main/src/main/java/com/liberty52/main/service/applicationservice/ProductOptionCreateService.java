package com.liberty52.main.service.applicationservice;

import com.liberty52.main.service.controller.dto.CreateProductOptionRequestDto;

public interface ProductOptionCreateService {
    void createProductOptionByAdmin(String role, CreateProductOptionRequestDto dto, String productId);
}
