package com.liberty52.product.service.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.liberty52.product.service.controller.dto.LicenseImageRetrieveDto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.liberty52.product.service.applicationservice.LicenseImageRetrieveService;
import com.liberty52.product.service.controller.dto.LicenseImageRetrieveByAdminDto;

import lombok.RequiredArgsConstructor;

@Tag(name = "이미지", description = "이미지 관련 API를 제공합니다")
@RestController
@RequiredArgsConstructor
public class LicenseImageRetrieveController {
	private final LicenseImageRetrieveService licenseImageRetrieveService;

	@Operation(summary = "라이선스 이미지 조회", description = "관리자 권한을 사용하여 라이선스 이미지를 조회합니다.")
	@GetMapping("/admin/licenseImage")
	@ResponseStatus(HttpStatus.OK)
	public List<LicenseImageRetrieveByAdminDto> retrieveLicenseImagesByAdmin(@RequestHeader("LB-Role") String role) {
		return licenseImageRetrieveService.retrieveLicenseImagesByAdmin(role);
	}

	@Operation(summary = "라이선스 이미지 상세 조회", description = "관리자 권한을 사용하여 라이선스 이미지를 상세 조회합니다.")
	@GetMapping("/admin/licenseImage/{licenseImageId}")
	@ResponseStatus(HttpStatus.OK)
	public LicenseImageRetrieveByAdminDto retrieveLicenseImageDetailsByAdmin(@RequestHeader("LB-Role") String role,
		@PathVariable String licenseImageId) {
		return licenseImageRetrieveService.retrieveLicenseImageDetailsByAdmin(role,licenseImageId);
	}
	@Operation(summary = "라이선스 이미지 조회", description = "사용자가 사용 가능한 라이선스 이미지를 조회합니다.")
	@GetMapping("/licenseImage")
	@ResponseStatus(HttpStatus.OK)
	public List<LicenseImageRetrieveDto> retrieveLicenseImages() {
		return licenseImageRetrieveService.retrieveLicenseImages();
	}

}
