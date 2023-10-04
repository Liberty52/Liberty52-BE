package com.liberty52.product.service.controller.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LicenseImageCreateDto {
	@NotBlank
	private String artistName;
	@NotBlank
	private String artName;
	@NotNull
	private LocalDate startDate;
	@NotNull
	private LocalDate endDate;
	@Min(0)
	private Integer stock;

	public static LicenseImageCreateDto createForTest(String artistName, String artName, LocalDate startDate,
		LocalDate endDate, Integer stock) {
		LicenseImageCreateDto dto = new LicenseImageCreateDto();
		dto.artistName = artistName;
		dto.artName = artName;
		dto.startDate = startDate;
		dto.endDate = endDate;
		dto.stock = stock;
		return dto;
	}
}
