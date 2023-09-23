package com.liberty52.product.service.applicationservice.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.liberty52.product.global.adapter.s3.S3UploaderApi;
import com.liberty52.product.global.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.product.global.util.Validator;
import com.liberty52.product.service.applicationservice.ProductIntroductionCreateService;
import com.liberty52.product.service.entity.Product;
import com.liberty52.product.service.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductIntroductionCreateServiceImpl implements ProductIntroductionCreateService {
	private final ProductRepository productRepository;
	private final S3UploaderApi s3Uploader;

	@Override
	public void createProductIntroduction(String role, String productId, MultipartFile productIntroductionImageFile) {
		Validator.isAdmin(role);
		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new ResourceNotFoundException("product", "id", productId));
		if (productIntroductionImageFile != null) {
			String imageUrl = uploadImage(productIntroductionImageFile);
			product.createProductIntroduction(imageUrl);
		}
	}

	private String uploadImage(MultipartFile multipartFile) {
		if (multipartFile == null)
			return null;
		return s3Uploader.upload(multipartFile);
	}
}
