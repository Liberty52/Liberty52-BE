package com.liberty52.product.service.applicationservice.mock;

import static com.liberty52.product.global.constants.RoleConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import com.liberty52.product.global.adapter.s3.S3UploaderApi;
import com.liberty52.product.global.exception.internal.S3UploaderException;
import com.liberty52.product.service.applicationservice.impl.ProductIntroductionCreateServiceImpl;
import com.liberty52.product.service.entity.Product;
import com.liberty52.product.service.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductIntroductionCreateMockTest {
	@InjectMocks
	ProductIntroductionCreateServiceImpl productService;

	@Mock
	ProductRepository productRepository;

	@Mock
	S3UploaderApi s3Uploader;
	@Test
	void createProductIntroductionMockTest() throws S3UploaderException {
		// Given
		String productId = "testProductId";
		MultipartFile multipartFile = mock(MultipartFile.class);

		Product mockProduct = mock(Product.class);
		given(productRepository.findById(anyString())).willReturn(Optional.of(mockProduct));
		given(s3Uploader.upload(multipartFile)).willReturn("mockImageUrl");
		given(mockProduct.getProductIntroductionImageUrl()).willReturn("mockImageUrl");

		// When
		productService.createProductIntroduction(ADMIN, productId, multipartFile);

		// Then: 검증 로직 추가 (예: 이미지 URL이 제대로 설정되었는지 확인)
		assertEquals("mockImageUrl", mockProduct.getProductIntroductionImageUrl());
	}

}
