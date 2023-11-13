package com.liberty52.product.service.controller;

import com.liberty52.product.service.applicationservice.LicenseImageCreateService;
import com.liberty52.product.service.applicationservice.LicenseImageDeleteService;
import com.liberty52.product.service.applicationservice.LicenseImageModifyService;
import com.liberty52.product.service.applicationservice.LicenseImageRetrieveService;
import com.liberty52.product.service.controller.dto.LicenseImageCreateDto;
import com.liberty52.product.service.controller.dto.LicenseImageModifyDto;
import com.liberty52.product.service.controller.dto.LicenseImageRetrieveByAdminDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "라이선스 이미지", description = "라이선스 이미지 관련 API를 제공합니다")
@RestController
@RequiredArgsConstructor
public class LicenseImageAdminController {
	private final LicenseImageCreateService licenseImageCreateService;
	private final LicenseImageRetrieveService licenseImageRetrieveService;
	private final LicenseImageModifyService licenseImageModifyService;
	private final LicenseImageDeleteService licenseImageDeleteService;

	@Operation(summary = "라이선스 이미지 생성", description = "관리자 권한을 사용하여 라이선스 이미지를 생성합니다.")
	@PostMapping("/admin/licenseImage")
	@ResponseStatus(HttpStatus.CREATED)
	public void createLicenseImage(@RequestHeader("LB-Role") String role,
		@RequestPart("dto") LicenseImageCreateDto dto,
		@RequestPart(value = "image") MultipartFile licenseImageFile) {
		licenseImageCreateService.createLicenseImage(role, dto, licenseImageFile);
	}

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
		return licenseImageRetrieveService.retrieveLicenseImageDetailsByAdmin(role, licenseImageId);
	}

	@Operation(summary = "라이선스 이미지 수정", description = "관리자 권한을 사용하여 라이선스 이미지를 수정합니다.")
	@PutMapping("/admin/licenseImage/{licenseImageId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void modifyLicenseImage(@RequestHeader("LB-Role") String role,
		@RequestPart("dto") LicenseImageModifyDto dto,
		@RequestPart(value = "image", required = false) MultipartFile licenseImageFile,
		@PathVariable String licenseImageId) {
		licenseImageModifyService.modifyLicenseImage(role, dto, licenseImageFile, licenseImageId);
	}

	@Operation(summary = "라이선스 이미지 삭제", description = "관리자 권한을 사용하여 라이선스 이미지를 삭제합니다.")
	@DeleteMapping("/admin/licenseImage/{licenseImageId}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteLicenseImage(@RequestHeader("LB-Role") String role, @PathVariable String licenseImageId) {
		licenseImageDeleteService.deleteLicenseImage(role, licenseImageId);
	}

}
