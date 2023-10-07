package com.liberty52.product.service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.liberty52.product.service.applicationservice.LicenseImageDeleteService;

import lombok.RequiredArgsConstructor;

@Tag(name = "이미지", description = "이미지 관련 API를 제공합니다")
@RestController
@RequiredArgsConstructor
public class LicenseImageDeleteController {
	private final LicenseImageDeleteService licenseImageDeleteService;

	@Operation(summary = "라이선스 이미지 삭제", description = "라이선스 이미지를 삭제합니다.")
	@DeleteMapping("/admin/licenseImage/{licenseImageId}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteLicenseImage(@RequestHeader("LB-Role") String role, @PathVariable String licenseImageId) {
		licenseImageDeleteService.deleteLicenseImage(role, licenseImageId);
	}
}
