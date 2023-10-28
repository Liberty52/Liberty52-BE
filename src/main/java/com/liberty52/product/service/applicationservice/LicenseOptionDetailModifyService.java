package com.liberty52.product.service.applicationservice;

import com.liberty52.product.service.controller.dto.LicenseOptionDetailModifyDto;

public interface LicenseOptionDetailModifyService {
    void modifyLicenseOptionDetailByAdmin(String role, String licenseOptionDetailId, LicenseOptionDetailModifyDto dto);

    void modifyLicenseOptionDetailOnSailStateByAdmin(String role, String licenseOptionDetailId);
}
