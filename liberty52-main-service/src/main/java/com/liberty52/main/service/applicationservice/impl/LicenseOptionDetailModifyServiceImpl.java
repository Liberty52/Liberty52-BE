package com.liberty52.main.service.applicationservice.impl;

import com.liberty52.common.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.main.global.adapter.s3.S3UploaderApi;
import com.liberty52.main.global.util.Validator;
import com.liberty52.main.service.applicationservice.LicenseOptionDetailModifyService;
import com.liberty52.main.service.controller.dto.LicenseOptionDetailModifyDto;
import com.liberty52.main.service.entity.license.LicenseOptionDetail;
import com.liberty52.main.service.repository.LicenseOptionDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class LicenseOptionDetailModifyServiceImpl implements LicenseOptionDetailModifyService {
    private final LicenseOptionDetailRepository licenseOptionDetailRepository;
    private final S3UploaderApi s3Uploader;

    @Override
    public void modifyLicenseOptionDetailByAdmin(String role, String licenseOptionDetailId,
                                                 LicenseOptionDetailModifyDto dto, MultipartFile artImageFile) {
        Validator.isAdmin(role);
        LicenseOptionDetail licenseOptionDetail = licenseOptionDetailRepository.findById(licenseOptionDetailId)
                .orElseThrow(() -> new ResourceNotFoundException("OptionDetail", "ID", licenseOptionDetailId));
        if (artImageFile != null) {
            licenseOptionDetail.modifyLicenseArtUrl(s3Uploader.upload(artImageFile));
        }
        licenseOptionDetail.modifyLicenseOptionDetail(dto);
    }

    @Override
    public void modifyLicenseOptionDetailOnSailStateByAdmin(String role, String licenseOptionDetailId) {
        Validator.isAdmin(role);
        LicenseOptionDetail licenseOptionDetail = licenseOptionDetailRepository.findById(licenseOptionDetailId)
                .orElseThrow(() -> new ResourceNotFoundException("OptionDetail", "ID", licenseOptionDetailId));
        licenseOptionDetail.updateOnSale();
    }
}
