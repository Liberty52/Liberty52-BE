package com.liberty52.product.service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "이미지", description = "이미지 관련 API를 제공합니다")
@RestController
@RequiredArgsConstructor
public class LicenseImageModifyController {
	private final LicenseImageModifyService licenseImageModifyService;

	@Operation(summary = "라이선스 이미지 수정", description = "관리자 권한을 사용하여 라이선스 이미지를 수정합니다.")
	@PutMapping("/admin/licenseImage/{licenseImageId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void modifyLicenseImage(@RequestHeader("LB-Role") String role,
		@RequestPart("dto") LicenseImageModifyDto dto,
		@RequestPart(value = "image", required = false) MultipartFile licenseImageFile,
		@PathVariable String licenseImageId) {
		licenseImageModifyService.modifyLicenseImage(role, dto, licenseImageFile, licenseImageId);
	}
}
