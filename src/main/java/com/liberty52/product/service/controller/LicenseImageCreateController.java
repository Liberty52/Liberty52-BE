package com.liberty52.product.service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.liberty52.product.service.applicationservice.LicenseImageCreateService;
import com.liberty52.product.service.controller.dto.LicenseImageCreateDto;

import lombok.RequiredArgsConstructor;

@Tag(name = "이미지", description = "이미지 관련 API를 제공합니다")
@RestController
@RequiredArgsConstructor
public class LicenseImageCreateController {
	private final LicenseImageCreateService licenseImageCreateService;

	@Operation(summary = "라이선스 이미지 생성", description = "라이선스 이미지를 생성합니다.")
	@PostMapping("/admin/licenseImage")
	@ResponseStatus(HttpStatus.CREATED)
	public void createLicenseImage(@RequestHeader("LB-Role") String role,
		@RequestPart("dto") LicenseImageCreateDto dto,
		@RequestPart(value = "image") MultipartFile licenseImageFile) {
		licenseImageCreateService.createLicenseImage(role, dto, licenseImageFile);
	}
}
