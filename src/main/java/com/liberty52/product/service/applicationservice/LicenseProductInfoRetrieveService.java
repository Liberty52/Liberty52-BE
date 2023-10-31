package com.liberty52.product.service.applicationservice;

import com.liberty52.product.service.controller.dto.LicenseOptionInfoResponseDto;
import com.liberty52.product.service.controller.dto.LicenseOptionResponseDto;

public interface LicenseProductInfoRetrieveService {
<<<<<<< HEAD
    List<LicenseOptionInfoResponseDto> retrieveLicenseProductOptionInfoListByAdmin(String role, String productId, boolean onSale);

    LicenseOptionResponseDto retrieveLicenseProductOptionInfo(String productId);
=======
	LicenseOptionInfoResponseDto retrieveLicenseProductOptionInfoListByAdmin(String role, String productId, boolean onSale);
>>>>>>> 6ddce74f302bc4626c1eabe6c561677dcf2cd02a
}
