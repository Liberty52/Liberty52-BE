package com.liberty52.product.service.applicationservice.impl;

import java.time.LocalDate;
import java.util.List;

import com.liberty52.product.service.controller.dto.LicenseImageRetrieveDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liberty52.product.global.util.Validator;
import com.liberty52.product.service.applicationservice.LicenseImageRetrieveService;
import com.liberty52.product.service.controller.dto.LicenseImageRetrieveByAdminDto;
import com.liberty52.product.service.entity.LicenseImage;
import com.liberty52.product.service.repository.LicenseImageRepository;

import lombok.RequiredArgsConstructor;

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
	public List<LicenseImageRetrieveDto> retrieveLicenseImages() {
		LocalDate today = LocalDate.now();
		List<LicenseImage> licenseImageList = licenseImageRepository.findByStartDateLessThanEqualAndEndDateGreaterThanEqual(today, today);
		return licenseImageList.stream()
				.map(LicenseImageRetrieveDto::new)
				.toList();
	}
}
