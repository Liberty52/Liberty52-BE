package com.liberty52.main.service.applicationservice.impl;

import com.liberty52.common.exception.external.notfound.NotFoundException;
import com.liberty52.main.global.util.Validator;
import com.liberty52.main.service.applicationservice.LicenseImageDeleteService;
import com.liberty52.main.service.entity.LicenseImage;
import com.liberty52.main.service.repository.LicenseImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LicenseImageDeleteServiceImpl implements LicenseImageDeleteService {

    private final LicenseImageRepository licenseImageRepository;

    @Override
    public void deleteLicenseImage(String role, String licenseImageId) {
        Validator.isAdmin(role);
        LicenseImage licenseImage = licenseImageRepository.findById(licenseImageId)
                .orElseThrow(() -> new NotFoundException("해당 라이센스 이미지가 존재하지 않습니다."));
        licenseImageRepository.delete(licenseImage);
    }
}
