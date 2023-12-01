package com.liberty52.main.service.applicationservice;

import com.liberty52.main.service.controller.dto.LicenseOptionCreateRequestDto;

public interface LicenseOptionCreateService {
    void createLicenseOptionByAdmin(String role, LicenseOptionCreateRequestDto dto, String productId);
}
