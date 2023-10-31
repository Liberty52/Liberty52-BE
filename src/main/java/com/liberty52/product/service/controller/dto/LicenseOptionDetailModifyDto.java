package com.liberty52.product.service.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LicenseOptionDetailModifyDto {
	@NotBlank
	String artName;
	@NotBlank
	String artistName;
	@NotNull
	@Min(0)
	Integer stock;
	@NotNull
	@Min(0)
	Integer price;
	@NotNull
	Boolean onSale;

	public static LicenseOptionDetailModifyDto create(String artName, String artistName, Integer stock, Boolean onSale,
		Integer price) {
		LicenseOptionDetailModifyDto dto = new LicenseOptionDetailModifyDto();
		dto.artName = artName;
		dto.artistName = artistName;
		dto.stock = stock;
		dto.onSale = onSale;
		dto.price = price;
		return dto;
	}

}
