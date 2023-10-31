package com.liberty52.product.service.applicationservice.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liberty52.product.global.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.product.global.util.Validator;
import com.liberty52.product.service.applicationservice.LicenseOptionModifyService;
import com.liberty52.product.service.controller.dto.LicenseOptionModifyRequestDto;
import com.liberty52.product.service.entity.license.LicenseOption;
import com.liberty52.product.service.repository.LicenseOptionRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class LicenseOptionModifyServiceImpl implements LicenseOptionModifyService {

	private final LicenseOptionRepository licenseOptionRepository;

	@Override
	public void modifyLicenseOptionByAdmin(String role, String licenseOptionId, LicenseOptionModifyRequestDto dto) {
		Validator.isAdmin(role);
		LicenseOption licenseOption = licenseOptionRepository.findById(licenseOptionId)
			.orElseThrow(() -> new ResourceNotFoundException("ProductOption", "ID", licenseOptionId));
		licenseOption.modifyLicenseOption(dto.getName());
	}
}
