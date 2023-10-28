package com.liberty52.product.service.applicationservice;

import com.liberty52.product.service.controller.dto.LicenseOptionCreateRequestDto;

public interface LicenseOptionCreateService {
    void createLicenseOptionByAdmin(String role, LicenseOptionCreateRequestDto dto, String productId);
}
