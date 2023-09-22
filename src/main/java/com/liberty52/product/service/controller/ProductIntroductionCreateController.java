package com.liberty52.product.service.controller;

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

@RestController
@RequiredArgsConstructor
public class ProductIntroductionCreateController {
	private final ProductIntroductionCreateService productIntroductionCreateService;

	@PostMapping("/admin/product/{productId}/introduction")
	@ResponseStatus(HttpStatus.CREATED)
	public void createProductIntroductionByAdmin(@RequestHeader("LB-Role") String role, @PathVariable String productId,
		@RequestPart(value = "images",required = false) MultipartFile productIntroductionImageFile) {
		productIntroductionCreateService.createProductIntroduction(role, productId, productIntroductionImageFile);
	}
}
