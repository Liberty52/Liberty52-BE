package com.liberty52.main.service.controller.license;

import com.liberty52.main.service.applicationservice.LicenseOptionDetailCreateService;
import com.liberty52.main.service.applicationservice.LicenseOptionDetailModifyService;
import com.liberty52.main.service.controller.dto.LicenseOptionDetailCreateDto;
import com.liberty52.main.service.controller.dto.LicenseOptionDetailModifyDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "라이선스 상품", description = "라이선스 상품 관련 API를 제공합니다")
@RestController
@RequiredArgsConstructor
public class LicenseOptionDetailAdminController {

    private final LicenseOptionDetailCreateService licenseOptionDetailCreateService;
    private final LicenseOptionDetailModifyService licenseOptionDetailModifyService;

    @Operation(summary = "라이선스 옵션 상세 생성", description = "관리자가 새로운 라이선스 옵션 상세를 생성합니다.")
    @PostMapping("/admin/licenseOptionDetail/{licenseOptionId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createLicenseOptionDetailByAdmin(@RequestHeader("LB-Role") String role,
                                                 @Validated @RequestPart(value = "dto") LicenseOptionDetailCreateDto dto, @PathVariable String licenseOptionId,
                                                 @RequestPart(value = "file") MultipartFile imageFile) {
        licenseOptionDetailCreateService.createLicenseOptionDetailByAdmin(role, dto, licenseOptionId, imageFile);
    }

    @Operation(summary = "라이선스 옵션 상세 수정", description = "관리자가 라이선스 옵션 상세 정보를 수정합니다.")
    @PutMapping("/admin/licenseOptionDetail/{licenseOptionDetailId}")
    @ResponseStatus(HttpStatus.OK)
    public void modifyLicenseOptionDetailByAdmin(
            @RequestHeader("LB-Role") String role,
            @PathVariable String licenseOptionDetailId,
            @Validated @RequestPart(value = "dto") LicenseOptionDetailModifyDto dto,
            @RequestPart(value = "file", required = false) MultipartFile imageFile
    ) {
        licenseOptionDetailModifyService.modifyLicenseOptionDetailByAdmin(role, licenseOptionDetailId, dto, imageFile);
    }

    @Operation(summary = "라이선스 옵션 상세 판매 상태 수정", description = "관리자가 라이선스 옵션 상세의 판매 상태를 수정합니다.")
    @PutMapping("/admin/licenseOptionDetailOnSale/{licenseOptionDetailId}")
    @ResponseStatus(HttpStatus.OK)
    public void modifyLicenseOptionDetailOnSailStateByAdmin(
            @RequestHeader("LB-Role") String role,
            @PathVariable String licenseOptionDetailId
    ) {
        licenseOptionDetailModifyService.modifyLicenseOptionDetailOnSailStateByAdmin(role, licenseOptionDetailId);
    }
}
