package com.liberty52.product.service.applicationservice;

import com.liberty52.product.service.controller.dto.LicenseOptionDetailCreateDto;

public interface LicenseOptionDetailCreateService {
    void createLicenseOptionDetailByAdmin(String role, LicenseOptionDetailCreateDto dto, String licenseOptionId);
}
