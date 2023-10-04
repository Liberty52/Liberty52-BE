package com.liberty52.product.service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liberty52.product.service.applicationservice.LicenseImageCreateService;
import com.liberty52.product.service.controller.dto.LicenseImageCreateDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LicenseImageCreateController {
	private final LicenseImageCreateService licenseImageCreateService;
	private final ObjectMapper objectMapper;

	@PostMapping("/admin/licenseImage")
	@ResponseStatus(HttpStatus.CREATED)
	public void createLicenseImage(@RequestHeader("LB-Role") String role,
		@RequestPart("dto") String dtoJson,
		@RequestPart(value = "image") MultipartFile licenseImageFile) throws JsonProcessingException {
		LicenseImageCreateDto dto = objectMapper.readValue(dtoJson, LicenseImageCreateDto.class);
		licenseImageCreateService.createLicenseImage(role, dto, licenseImageFile);
	}
}
