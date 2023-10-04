package com.liberty52.product.service.applicationservice.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.liberty52.product.global.adapter.s3.S3UploaderApi;
import com.liberty52.product.global.util.Validator;
import com.liberty52.product.service.applicationservice.LicenseImageCreateService;
import com.liberty52.product.service.controller.dto.LicenseImageCreateDto;
import com.liberty52.product.service.entity.LicenseImage;
import com.liberty52.product.service.repository.LicenseImageRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class LicenseImageCreateServiceImpl implements LicenseImageCreateService {

	private final LicenseImageRepository licenseImageRepository;
	private final S3UploaderApi s3Uploader;

	@Override
	public void createLicenseImage(String role, LicenseImageCreateDto dto, MultipartFile licenseImageFile) {
		Validator.isAdmin(role);
		LicenseImage licenseImage = LicenseImage.builder()
			.artistName(dto.getArtistName())
			.artName(dto.getArtName())
			.startDate(dto.getStartDate())
			.endDate(dto.getEndDate())
			.licenseImageUrl(s3Uploader.upload(licenseImageFile))
			.stock(dto.getStock())
			.build();
		licenseImageRepository.save(licenseImage);
	}
}
