package com.liberty52.product.service.applicationservice;

import org.springframework.web.multipart.MultipartFile;

import com.liberty52.product.service.controller.dto.LicenseOptionDetailModifyDto;

public interface LicenseOptionDetailModifyService {
    void modifyLicenseOptionDetailByAdmin(String role, String licenseOptionDetailId, LicenseOptionDetailModifyDto dto,
        MultipartFile artImageFile);

    void modifyLicenseOptionDetailOnSailStateByAdmin(String role, String licenseOptionDetailId);
}
