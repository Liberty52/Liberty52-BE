package com.liberty52.product.service.applicationservice.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liberty52.product.global.exception.external.badrequest.BadRequestException;
import com.liberty52.product.global.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.product.global.util.Validator;
import com.liberty52.product.service.applicationservice.LicenseOptionCreateService;
import com.liberty52.product.service.controller.dto.LicenseOptionCreateRequestDto;
import com.liberty52.product.service.entity.Product;
import com.liberty52.product.service.entity.license.LicenseOption;
import com.liberty52.product.service.repository.LicenseOptionRepository;
import com.liberty52.product.service.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class LicenseOptionCreateServiceImpl implements LicenseOptionCreateService {

	private final ProductRepository productRepository;
	private final LicenseOptionRepository licenseOptionRepository;

	@Override
	public void createLicenseOptionByAdmin(String role, LicenseOptionCreateRequestDto dto, String productId) {
		Validator.isAdmin(role);
		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new ResourceNotFoundException("productId", "id", productId));
		if (licenseOptionRepository.findByName(dto.getName()).isPresent()) {
			throw new BadRequestException("이미 존재하는 라이선스 옵션 입니다");
		}

		LicenseOption licenseOption = LicenseOption.create(dto.getName());
		licenseOption.associate(product);
		licenseOptionRepository.save(licenseOption);
	}
}
