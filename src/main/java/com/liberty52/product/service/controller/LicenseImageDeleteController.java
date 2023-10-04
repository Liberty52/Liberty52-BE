package com.liberty52.product.service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.liberty52.product.service.applicationservice.LicenseImageDeleteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LicenseImageDeleteController {
	private final LicenseImageDeleteService licenseImageDeleteService;

	@DeleteMapping("/admin/licenseImage/{licenseImageId}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteLicenseImage(@RequestHeader("LB-Role") String role, @PathVariable String licenseImageId) {
		licenseImageDeleteService.deleteLicenseImage(role, licenseImageId);
	}
}
