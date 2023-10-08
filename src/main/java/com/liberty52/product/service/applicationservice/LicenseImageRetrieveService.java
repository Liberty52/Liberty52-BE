package com.liberty52.product.service.applicationservice;

import java.util.List;

import com.liberty52.product.service.controller.dto.LicenseImageRetrieveDto;

public interface LicenseImageRetrieveService {
	List<LicenseImageRetrieveDto> retrieveLicenseImages(String role);
}
