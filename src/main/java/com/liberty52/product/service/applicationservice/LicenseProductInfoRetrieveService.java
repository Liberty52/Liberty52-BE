package com.liberty52.product.service.applicationservice;

import java.util.List;

import com.liberty52.product.service.controller.dto.LicenseOptionInfoResponseDto;
import com.liberty52.product.service.controller.dto.LicenseOptionResponseDto;

public interface LicenseProductInfoRetrieveService {
    List<LicenseOptionInfoResponseDto> retrieveLicenseProductOptionInfoListByAdmin(String role, String productId, boolean onSale);

    LicenseOptionResponseDto retrieveLicenseProductOptionInfo(String productId);
}
