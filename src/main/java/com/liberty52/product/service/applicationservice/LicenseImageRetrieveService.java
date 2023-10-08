package com.liberty52.product.service.applicationservice;

import java.util.List;

import com.liberty52.product.service.controller.dto.LicenseImageRetrieveByAdminDto;

public interface LicenseImageRetrieveService {
	List<LicenseImageRetrieveByAdminDto> retrieveLicenseImagesByAdmin(String role);
}
