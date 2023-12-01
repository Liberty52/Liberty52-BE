package com.liberty52.main.service.applicationservice.impl;

import com.liberty52.common.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.main.global.util.Validator;
import com.liberty52.main.service.applicationservice.LicenseOptionModifyService;
import com.liberty52.main.service.controller.dto.LicenseOptionModifyRequestDto;
import com.liberty52.main.service.entity.license.LicenseOption;
import com.liberty52.main.service.repository.LicenseOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LicenseOptionModifyServiceImpl implements LicenseOptionModifyService {

    private final LicenseOptionRepository licenseOptionRepository;

    @Override
    public void modifyLicenseOptionByAdmin(String role, String licenseOptionId, LicenseOptionModifyRequestDto dto) {
        Validator.isAdmin(role);
        LicenseOption licenseOption = licenseOptionRepository.findById(licenseOptionId)
                .orElseThrow(() -> new ResourceNotFoundException("ProductOption", "ID", licenseOptionId));
        licenseOption.modifyLicenseOption(dto.getName());
    }
}
