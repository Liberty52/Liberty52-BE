package com.liberty52.main.service.applicationservice;

import com.liberty52.main.service.controller.dto.LicenseImageCreateDto;
import org.springframework.web.multipart.MultipartFile;

public interface LicenseImageCreateService {
    void createLicenseImage(String role, LicenseImageCreateDto dto, MultipartFile productIntroductionImageFile);
}
