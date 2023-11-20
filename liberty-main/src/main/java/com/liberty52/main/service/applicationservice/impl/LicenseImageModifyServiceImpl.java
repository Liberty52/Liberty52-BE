package com.liberty52.main.service.applicationservice.impl;

import com.liberty52.main.global.adapter.s3.S3UploaderApi;
import com.liberty52.main.global.exception.external.notfound.NotFoundException;
import com.liberty52.main.global.util.Validator;
import com.liberty52.main.service.applicationservice.LicenseImageModifyService;
import com.liberty52.main.service.controller.dto.LicenseImageModifyDto;
import com.liberty52.main.service.entity.LicenseImage;
import com.liberty52.main.service.repository.LicenseImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class LicenseImageModifyServiceImpl implements LicenseImageModifyService {

    private final LicenseImageRepository licenseImageRepository;
    private final S3UploaderApi s3Uploader;

    @Override
    public void modifyLicenseImage(String role, LicenseImageModifyDto dto, MultipartFile licenseImageFile,
                                   String licenseImageId) {
        Validator.isAdmin(role);
        LicenseImage licenseImage = licenseImageRepository.findById(licenseImageId)
                .orElseThrow(() -> new NotFoundException("해당 라이센스 이미지가 존재하지 않습니다."));
        if (licenseImageFile != null) {
            String licenseImageUrl = s3Uploader.upload(licenseImageFile);
            licenseImage.updateLicenseImageUrl(licenseImageUrl);
        }
        licenseImage.updateLicense(dto);
    }
}
