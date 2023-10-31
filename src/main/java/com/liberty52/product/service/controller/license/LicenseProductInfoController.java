package com.liberty52.product.service.controller.license;

import java.util.List;

import com.liberty52.product.service.controller.dto.LicenseOptionResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.liberty52.product.service.applicationservice.LicenseProductInfoRetrieveService;
import com.liberty52.product.service.controller.dto.LicenseOptionInfoResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

//TODO: 조회 시 리스트가 아닌 단일로 조회 리팩토링
@Tag(name = "라이선스 상품 정보", description = "라이선스 상품 정보 관련 API를 제공합니다")
@RestController
@RequiredArgsConstructor
public class LicenseProductInfoController {
	private final LicenseProductInfoRetrieveService licenseProductInfoRetrieveService;

	@Operation(summary = "관리자용 라이선스 상품 옵션 정보 조회", description = "관리자가 특정 라이선스 상품의 옵션 정보를 조회합니다.")
	@GetMapping("/admin/licenseProductOptionInfo/{productId}")
	@ResponseStatus(HttpStatus.OK)
	public LicenseOptionInfoResponseDto retrieveLicenseProductOptionInfoListByAdmin(
		@RequestHeader("LB-Role") String role, @PathVariable String productId, @RequestParam boolean onSale) {
		return licenseProductInfoRetrieveService.retrieveLicenseProductOptionInfoListByAdmin(role, productId, onSale);
	}

	@Operation(summary = "관리자용 라이선스 상품 옵션 정보 조회", description = "관리자가 특정 라이선스 상품의 옵션 정보를 조회합니다.")
	@GetMapping("/licenseProductOptionInfo/{productId}")
	@ResponseStatus(HttpStatus.OK)
	public LicenseOptionResponseDto retrieveLicenseProductOptionInfoList(@PathVariable String productId) {
		return licenseProductInfoRetrieveService.retrieveLicenseProductOptionInfo(productId);
	}
}
