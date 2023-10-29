package com.liberty52.product.service.applicationservice.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.liberty52.product.global.adapter.s3.S3UploaderApi;
import com.liberty52.product.global.exception.external.badrequest.BadRequestException;
import com.liberty52.product.global.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.product.global.util.Validator;
import com.liberty52.product.service.applicationservice.LicenseOptionDetailModifyService;
import com.liberty52.product.service.controller.dto.LicenseOptionDetailModifyDto;
import com.liberty52.product.service.entity.license.LicenseOptionDetail;
import com.liberty52.product.service.repository.LicenseOptionDetailRepository;

import lombok.RequiredArgsConstructor;

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
		if (artImageFile == null) {
			throw new BadRequestException("ArtImageFile must be not null");
		}
		licenseOptionDetail.modifyLicenseOptionDetail(dto, s3Uploader.upload(artImageFile));
	}

	@Override
	public void modifyLicenseOptionDetailOnSailStateByAdmin(String role, String licenseOptionDetailId) {
		Validator.isAdmin(role);
		LicenseOptionDetail licenseOptionDetail = licenseOptionDetailRepository.findById(licenseOptionDetailId)
			.orElseThrow(() -> new ResourceNotFoundException("OptionDetail", "ID", licenseOptionDetailId));
		licenseOptionDetail.updateOnSale();
	}
}
