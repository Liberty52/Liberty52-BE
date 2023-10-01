package com.liberty52.product.service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.liberty52.product.service.applicationservice.LicenseImageCreateService;
import com.liberty52.product.service.controller.dto.LicenseImageCreateDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LicenseImageCreateController {
	private final LicenseImageCreateService licenseImageCreateService;

	@PostMapping("/admin/licenseImage")
	@ResponseStatus(HttpStatus.CREATED)
	public void createLicenseImage(@RequestHeader("LB-Role") String role,
		@RequestPart("dto") @Validated LicenseImageCreateDto dto,
		@RequestPart(value = "image") MultipartFile licenseImageFile) {
		licenseImageCreateService.createLicenseImage(role, dto, licenseImageFile);
	}
}
