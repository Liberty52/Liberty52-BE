package com.liberty52.product.service.applicationservice.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.liberty52.product.global.adapter.s3.S3UploaderApi;
import com.liberty52.product.global.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.product.global.util.Validator;
import com.liberty52.product.service.applicationservice.LicenseOptionDetailCreateService;
import com.liberty52.product.service.controller.dto.LicenseOptionDetailCreateDto;
import com.liberty52.product.service.entity.license.LicenseOption;
import com.liberty52.product.service.entity.license.LicenseOptionDetail;
import com.liberty52.product.service.repository.LicenseOptionDetailRepository;
import com.liberty52.product.service.repository.LicenseOptionRepository;

import lombok.RequiredArgsConstructor;

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
		LicenseOptionDetail licenseOptionDetail = LicenseOptionDetail.create(dto.getArtName(), dto.getArtistName(),
			dto.getStock(), dto.getOnSale(), s3Uploader.upload(artImageFile));
		licenseOptionDetail.associate(licenseOption);
		licenseOptionDetailRepository.save(licenseOptionDetail);
	}
}
