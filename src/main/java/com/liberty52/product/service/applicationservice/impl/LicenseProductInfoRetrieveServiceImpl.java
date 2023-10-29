package com.liberty52.product.service.applicationservice.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.liberty52.product.global.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.product.global.util.Validator;
import com.liberty52.product.service.applicationservice.LicenseProductInfoRetrieveService;
import com.liberty52.product.service.controller.dto.LicenseOptionInfoResponseDto;
import com.liberty52.product.service.entity.Product;
import com.liberty52.product.service.entity.license.LicenseOption;
import com.liberty52.product.service.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LicenseProductInfoRetrieveServiceImpl implements LicenseProductInfoRetrieveService {
	private final ProductRepository productRepository;

	@Override
	public List<LicenseOptionInfoResponseDto> retrieveLicenseProductOptionInfoListByAdmin(String role,
		String productId, boolean onSale) {
		Validator.isAdmin(role);
		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new ResourceNotFoundException("product", "id", productId));
		if (product.getLicenseOptions().isEmpty()) {
			return Collections.emptyList();
		}

		if (onSale) {
			return product.getLicenseOptions()
				.stream()
				.filter(LicenseOption::getOnSale)
				.map(licenseOption -> new LicenseOptionInfoResponseDto(licenseOption, true))
				.toList();
		} else {
			return product.getLicenseOptions()
				.stream()
				.sorted(Comparator.comparing(LicenseOption::getOnSale).reversed())
				.map(licenseOption -> new LicenseOptionInfoResponseDto(licenseOption, false))
				.toList();
		}
	}
}
