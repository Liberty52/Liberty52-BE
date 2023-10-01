package com.liberty52.product.service.applicationservice.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.liberty52.product.global.adapter.s3.S3UploaderApi;
import com.liberty52.product.global.util.Validator;
import com.liberty52.product.service.applicationservice.LicenseImageCreateService;
import com.liberty52.product.service.applicationservice.LicenseImageRetrieveService;
import com.liberty52.product.service.controller.dto.LicenseImageCreateDto;
import com.liberty52.product.service.controller.dto.LicenseImageRetrieveDto;
import com.liberty52.product.service.entity.LicenseImage;
import com.liberty52.product.service.repository.LicenseImageRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class LicenseImageRetrieveServiceImpl implements LicenseImageRetrieveService {
	private final LicenseImageRepository licenseImageRepository;

	@Override
	public List<LicenseImageRetrieveDto> retrieveLicenseImages(String role) {
		Validator.isAdmin(role);
		List<LicenseImage> licenseImageList = licenseImageRepository.findAll();
		return licenseImageList.stream()
			.map(LicenseImageRetrieveDto::new)
			.collect(Collectors.toList());
	}
}
