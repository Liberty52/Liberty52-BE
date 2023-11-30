package com.liberty52.main.service.applicationservice;

import com.liberty52.main.service.controller.dto.LicenseOptionDetailCreateDto;
import org.springframework.web.multipart.MultipartFile;

public interface LicenseOptionDetailCreateService {
    void createLicenseOptionDetailByAdmin(String role, LicenseOptionDetailCreateDto dto, String licenseOptionId,
                                          MultipartFile artImageFile);
}
