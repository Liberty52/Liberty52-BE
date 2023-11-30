package com.liberty52.main.service.controller.license;

import com.liberty52.main.service.applicationservice.LicenseProductInfoRetrieveService;
import com.liberty52.main.service.controller.dto.LicenseOptionResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "라이선스 상품 정보", description = "라이선스 상품 정보 관련 API를 제공합니다")
@RestController
@RequiredArgsConstructor
public class LicenseProductInfoController {
    private final LicenseProductInfoRetrieveService licenseProductInfoRetrieveService;

    @Operation(summary = "라이선스 상품 옵션 정보 조회", description = "사용자가 특정 라이선스 상품의 옵션 정보를 조회합니다.")
    @GetMapping("/licenseProductOptionInfo/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public LicenseOptionResponseDto retrieveLicenseProductOptionInfoList(@PathVariable String productId) {
        return licenseProductInfoRetrieveService.retrieveLicenseProductOptionInfo(productId);
    }
}
