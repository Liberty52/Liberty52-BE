package com.liberty52.main.service.applicationservice.impl;

import com.liberty52.main.global.exception.external.notfound.NotFoundException;
import com.liberty52.main.global.util.Validator;
import com.liberty52.main.service.applicationservice.LicenseImageRetrieveService;
import com.liberty52.main.service.controller.dto.LicenseImageRetrieveByAdminDto;
import com.liberty52.main.service.controller.dto.LicenseImageRetrieveDto;
import com.liberty52.main.service.entity.LicenseImage;
import com.liberty52.main.service.repository.LicenseImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LicenseImageRetrieveServiceImpl implements LicenseImageRetrieveService {
    private final LicenseImageRepository licenseImageRepository;

    @Override
    public List<LicenseImageRetrieveByAdminDto> retrieveLicenseImagesByAdmin(String role) {
        Validator.isAdmin(role);
        List<LicenseImage> licenseImageList = licenseImageRepository.findAll();
        return licenseImageList.stream()
                .map(LicenseImageRetrieveByAdminDto::new)
                .toList();
    }

    @Override
    public LicenseImageRetrieveByAdminDto retrieveLicenseImageDetailsByAdmin(String role, String licenseImageId) {
        Validator.isAdmin(role);
        return licenseImageRepository.findById(licenseImageId)
                .map(LicenseImageRetrieveByAdminDto::new)
                .orElseThrow(() -> new NotFoundException("해당 이미지가 존재하지 않습니다."));
    }

    @Override
    public List<LicenseImageRetrieveDto> retrieveLicenseImages() {
        LocalDate today = LocalDate.now();
        List<LicenseImage> licenseImageList = licenseImageRepository.findByStartDateLessThanEqualAndEndDateGreaterThanEqual(today, today);
        return licenseImageList.stream()
                .map(LicenseImageRetrieveDto::new)
                .toList();
    }
}
