package com.liberty52.product.service.applicationservice;

import java.util.List;

import com.liberty52.product.service.controller.dto.LicenseImageRetrieveByAdminDto;
import com.liberty52.product.service.controller.dto.LicenseImageRetrieveDto;

public interface LicenseImageRetrieveService {
	List<LicenseImageRetrieveByAdminDto> retrieveLicenseImagesByAdmin(String role);

	LicenseImageRetrieveByAdminDto retrieveLicenseImageDetailsByAdmin(String role, String licenseImageId);

	List<LicenseImageRetrieveDto> retrieveLicenseImages();

}
