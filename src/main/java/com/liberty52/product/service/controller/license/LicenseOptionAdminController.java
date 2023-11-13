package com.liberty52.product.service.controller.license;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.liberty52.product.service.applicationservice.LicenseOptionCreateService;
import com.liberty52.product.service.applicationservice.LicenseOptionModifyService;
import com.liberty52.product.service.controller.dto.LicenseOptionCreateRequestDto;
import com.liberty52.product.service.controller.dto.LicenseOptionModifyRequestDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "라이선스 상품", description = "라이선스 상품 관련 API를 제공합니다")
@RestController
@RequiredArgsConstructor
public class LicenseOptionAdminController {
	private final LicenseOptionCreateService licenseOptionCreateService;
	private final LicenseOptionModifyService licenseOptionModifyService;

	@Operation(summary = "라이선스 상품 옵션 생성", description = "관리자가 특정 라이선스 상품에 옵션을 생성합니다.")
	@PostMapping("/admin/licenseOption/{productId}")
	@ResponseStatus(HttpStatus.CREATED)
	public void createLicenseOptionByAdmin(@RequestHeader("LB-Role") String role,
		@Validated @RequestBody LicenseOptionCreateRequestDto dto, @PathVariable String productId) {
		licenseOptionCreateService.createLicenseOptionByAdmin(role, dto, productId);
	}

	@Operation(summary = "라이선스 상품 옵션 수정", description = "관리자가 특정 라이선스 상품 옵션을 수정합니다.")
	@PutMapping("/admin/licenseOption/{licenseOptionId}")
	@ResponseStatus(HttpStatus.OK)
	public void modifyLicenseOptionByAdmin(@RequestHeader("LB-Role") String role, @PathVariable String licenseOptionId,
		@Validated @RequestBody LicenseOptionModifyRequestDto dto) {
		licenseOptionModifyService.modifyLicenseOptionByAdmin(role, licenseOptionId, dto);
	}
}
