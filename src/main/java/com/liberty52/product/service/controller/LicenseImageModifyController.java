package com.liberty52.product.service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.liberty52.product.service.applicationservice.LicenseImageModifyService;
import com.liberty52.product.service.controller.dto.LicenseImageModifyDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LicenseImageModifyController {
	private final LicenseImageModifyService licenseImageModifyService;

	@PutMapping("/admin/licenseImage/{licenseImageId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void modifyLicenseImage(@RequestHeader("LB-Role") String role,
		@RequestPart("dto") LicenseImageModifyDto dto,
		@RequestPart(value = "image", required = false) MultipartFile licenseImageFile,
		@PathVariable String licenseImageId) {
		licenseImageModifyService.modifyLicenseImage(role, dto, licenseImageFile, licenseImageId);
	}
}
