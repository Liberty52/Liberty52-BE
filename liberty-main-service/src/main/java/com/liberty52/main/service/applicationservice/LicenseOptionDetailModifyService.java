package com.liberty52.main.service.applicationservice;

import com.liberty52.main.service.controller.dto.LicenseOptionDetailModifyDto;
import org.springframework.web.multipart.MultipartFile;

public interface LicenseOptionDetailModifyService {
    void modifyLicenseOptionDetailByAdmin(String role, String licenseOptionDetailId, LicenseOptionDetailModifyDto dto,
                                          MultipartFile artImageFile);

    void modifyLicenseOptionDetailOnSailStateByAdmin(String role, String licenseOptionDetailId);
}
