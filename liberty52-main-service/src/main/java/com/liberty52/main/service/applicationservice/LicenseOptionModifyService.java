package com.liberty52.main.service.applicationservice;

import com.liberty52.main.service.controller.dto.LicenseOptionModifyRequestDto;

public interface LicenseOptionModifyService {
    void modifyLicenseOptionByAdmin(String role, String licenseOptionId, LicenseOptionModifyRequestDto dto);
}
