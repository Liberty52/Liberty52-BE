package com.liberty52.product.service.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LicenseOptionModifyRequestDto {

	@NotBlank
	String name;
	@NotNull
	Boolean require;
	@NotNull
	Boolean onSale;

	public static LicenseOptionModifyRequestDto create(String name, Boolean require, Boolean onSale) {
		return new LicenseOptionModifyRequestDto(name, require, onSale);
	}
}
