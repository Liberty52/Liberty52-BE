package com.liberty52.product.service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.liberty52.product.service.applicationservice.ProductIntroductionCreateService;

import lombok.RequiredArgsConstructor;

@Tag(name = "상품", description = "상품 관련 API를 제공합니다")
@RestController
@RequiredArgsConstructor
public class ProductIntroductionCreateController {
	private final ProductIntroductionCreateService productIntroductionCreateService;

	@Operation(summary = "상품 소개 생성", description = "관리자가 특정 상품의 소개 이미지를 업로드하여 상품 소개를 생성합니다.")
	@PostMapping("/admin/product/{productId}/introduction")
	@ResponseStatus(HttpStatus.CREATED)
	public void createProductIntroductionByAdmin(@RequestHeader("LB-Role") String role, @PathVariable String productId,
		@RequestPart(value = "images",required = false) MultipartFile productIntroductionImageFile) {
		productIntroductionCreateService.createProductIntroduction(role, productId, productIntroductionImageFile);
	}
}
