package com.liberty52.product.service.controller.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class LicenseOptionDetailCreateDto {

	@NotBlank
	String artName;
	@NotBlank
	String artistName;
	@NotNull
	@Min(0)
	Integer stock;
	@NotNull
	Boolean onSale;
	@Min(0)
	@NotNull
	Integer price;
	@NotNull
	LocalDate startDate;
	@NotNull
	LocalDate endDate;

	public static LicenseOptionDetailCreateDto create(String artName, String artistName, Integer stock, Boolean onSale,
		Integer price, LocalDate startDate, LocalDate endDate) {
		LicenseOptionDetailCreateDto dto = new LicenseOptionDetailCreateDto();
		dto.artName = artName;
		dto.artistName = artistName;
		dto.stock = stock;
		dto.onSale = onSale;
		dto.price = price;
		dto.startDate = startDate;
		dto.endDate = endDate;
		return dto;
	}

}
