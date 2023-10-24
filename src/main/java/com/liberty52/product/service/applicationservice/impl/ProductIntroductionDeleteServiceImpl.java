package com.liberty52.product.service.applicationservice.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.liberty52.product.global.adapter.s3.S3UploaderApi;
import com.liberty52.product.global.exception.external.badrequest.BadRequestException;
import com.liberty52.product.global.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.product.global.util.Validator;
import com.liberty52.product.service.applicationservice.ProductIntroductionDeleteService;
import com.liberty52.product.service.applicationservice.ProductIntroductionUpsertService;
import com.liberty52.product.service.entity.Product;
import com.liberty52.product.service.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductIntroductionDeleteServiceImpl implements ProductIntroductionDeleteService {
	private final ProductRepository productRepository;

	@Override
	public void deleteProductIntroduction(String role, String productId) {
		Validator.isAdmin(role);
		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new ResourceNotFoundException("product", "id", productId));
		product.deleteContent();
	}
}
