package com.liberty52.main.service.applicationservice;

import com.liberty52.main.service.controller.dto.LicenseImageRetrieveByAdminDto;
import com.liberty52.main.service.controller.dto.LicenseImageRetrieveDto;

import java.util.List;

public interface LicenseImageRetrieveService {
    List<LicenseImageRetrieveByAdminDto> retrieveLicenseImagesByAdmin(String role);

    LicenseImageRetrieveByAdminDto retrieveLicenseImageDetailsByAdmin(String role, String licenseImageId);

    List<LicenseImageRetrieveDto> retrieveLicenseImages();

}
