package com.liberty52.product.service.applicationservice.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.liberty52.product.global.adapter.s3.S3UploaderApi;
import com.liberty52.product.global.exception.external.notfound.NotFoundException;
import com.liberty52.product.global.util.Validator;
import com.liberty52.product.service.applicationservice.LicenseImageDeleteService;
import com.liberty52.product.service.applicationservice.LicenseImageModifyService;
import com.liberty52.product.service.controller.dto.LicenseImageModifyDto;
import com.liberty52.product.service.entity.LicenseImage;
import com.liberty52.product.service.repository.LicenseImageRepository;

import lombok.RequiredArgsConstructor;

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
