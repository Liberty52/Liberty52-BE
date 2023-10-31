package com.liberty52.product.service.applicationservice;

import com.liberty52.product.service.controller.dto.LicenseOptionInfoResponseDto;

public interface LicenseProductInfoRetrieveService {
	LicenseOptionInfoResponseDto retrieveLicenseProductOptionInfoListByAdmin(String role, String productId, boolean onSale);
}
