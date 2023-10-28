package com.liberty52.product.service.applicationservice.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Override
	public void modifyLicenseOptionDetailByAdmin(String role, String licenseOptionDetailId,
		LicenseOptionDetailModifyDto dto) {
		Validator.isAdmin(role);
		LicenseOptionDetail licenseOptionDetail = licenseOptionDetailRepository.findById(licenseOptionDetailId)
			.orElseThrow(() -> new ResourceNotFoundException("OptionDetail", "ID", licenseOptionDetailId));
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
