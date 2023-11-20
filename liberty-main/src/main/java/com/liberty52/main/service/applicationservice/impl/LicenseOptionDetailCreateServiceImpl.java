package com.liberty52.main.service.applicationservice.impl;

import com.liberty52.main.global.adapter.s3.S3UploaderApi;
import com.liberty52.main.global.exception.external.badrequest.BadRequestException;
import com.liberty52.main.global.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.main.global.util.Validator;
import com.liberty52.main.service.applicationservice.LicenseOptionDetailCreateService;
import com.liberty52.main.service.controller.dto.LicenseOptionDetailCreateDto;
import com.liberty52.main.service.entity.license.LicenseOption;
import com.liberty52.main.service.entity.license.LicenseOptionDetail;
import com.liberty52.main.service.repository.LicenseOptionDetailRepository;
import com.liberty52.main.service.repository.LicenseOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class LicenseOptionDetailCreateServiceImpl implements LicenseOptionDetailCreateService {
    private final LicenseOptionRepository licenseOptionRepository;
    private final LicenseOptionDetailRepository licenseOptionDetailRepository;
    private final S3UploaderApi s3Uploader;

    @Override
    public void createLicenseOptionDetailByAdmin(String role, LicenseOptionDetailCreateDto dto, String licenseOptionId,
                                                 MultipartFile artImageFile) {
        Validator.isAdmin(role);
        LicenseOption licenseOption = licenseOptionRepository.findById(licenseOptionId)
                .orElseThrow(() -> new ResourceNotFoundException("option", "id", licenseOptionId));
        if (artImageFile == null) {
            throw new BadRequestException("라이선스 이미지가 없습니다.");
        }
        LicenseOptionDetail licenseOptionDetail = LicenseOptionDetail.create(dto.getArtName(), dto.getArtistName(),
                dto.getStock(), dto.getOnSale(), s3Uploader.upload(artImageFile), dto.getPrice(), dto.getStartDate(), dto.getEndDate());
        licenseOptionDetail.associate(licenseOption);
        licenseOptionDetailRepository.save(licenseOptionDetail);
    }
}
