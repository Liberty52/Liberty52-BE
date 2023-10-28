package com.liberty52.product.service.applicationservice;

import com.liberty52.product.service.controller.dto.LicenseOptionModifyRequestDto;

public interface LicenseOptionModifyService {
    void modifyLicenseOptionByAdmin(String role, String licenseOptionId, LicenseOptionModifyRequestDto dto);
}
