package com.liberty52.main.service.applicationservice;

import com.liberty52.main.service.controller.dto.LicenseOptionInfoResponseDto;
import com.liberty52.main.service.controller.dto.LicenseOptionResponseDto;

public interface LicenseProductInfoRetrieveService {
    LicenseOptionResponseDto retrieveLicenseProductOptionInfo(String productId);

    LicenseOptionInfoResponseDto retrieveLicenseProductOptionInfoListByAdmin(String role, String productId, boolean onSale);
}
