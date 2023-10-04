package com.liberty52.product.service.applicationservice;

import org.springframework.web.multipart.MultipartFile;

import com.liberty52.product.service.controller.dto.LicenseImageCreateDto;

public interface LicenseImageCreateService {
	void createLicenseImage(String role, LicenseImageCreateDto dto, MultipartFile productIntroductionImageFile);
}
