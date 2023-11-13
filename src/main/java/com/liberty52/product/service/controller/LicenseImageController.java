package com.liberty52.product.service.controller;

import com.liberty52.product.service.applicationservice.LicenseImageRetrieveService;
import com.liberty52.product.service.controller.dto.LicenseImageRetrieveDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "라이선스 이미지", description = "라이선스 이미지 관련 API를 제공합니다")
@RestController
@RequiredArgsConstructor
public class LicenseImageController {
	private final LicenseImageRetrieveService licenseImageRetrieveService;

	@Operation(summary = "라이선스 이미지 조회", description = "사용자가 사용 가능한 라이선스 이미지를 조회합니다.")
	@GetMapping("/licenseImage")
	@ResponseStatus(HttpStatus.OK)
	public List<LicenseImageRetrieveDto> retrieveLicenseImages() {
		return licenseImageRetrieveService.retrieveLicenseImages();
	}

}
