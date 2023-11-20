package com.liberty52.main.service.applicationservice;

import com.liberty52.main.service.controller.dto.LicenseImageModifyDto;
import org.springframework.web.multipart.MultipartFile;

public interface LicenseImageModifyService {
    void modifyLicenseImage(String role, LicenseImageModifyDto dto, MultipartFile licenseImageFileUrl, String licenseImageId);

}
