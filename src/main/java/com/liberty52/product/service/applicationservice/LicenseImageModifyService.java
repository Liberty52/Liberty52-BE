package com.liberty52.product.service.applicationservice;

import org.springframework.web.multipart.MultipartFile;

import com.liberty52.product.service.controller.dto.LicenseImageModifyDto;

public interface LicenseImageModifyService {
	void modifyLicenseImage(String role, LicenseImageModifyDto dto, MultipartFile licenseImageFileUrl, String licenseImageId);

}
