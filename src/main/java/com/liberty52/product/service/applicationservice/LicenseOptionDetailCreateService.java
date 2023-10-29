package com.liberty52.product.service.applicationservice;

import org.springframework.web.multipart.MultipartFile;

import com.liberty52.product.service.controller.dto.LicenseOptionDetailCreateDto;

public interface LicenseOptionDetailCreateService {
	void createLicenseOptionDetailByAdmin(String role, LicenseOptionDetailCreateDto dto, String licenseOptionId,
		MultipartFile artImageFile);
}
