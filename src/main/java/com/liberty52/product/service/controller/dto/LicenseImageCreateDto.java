package com.liberty52.product.service.controller.dto;

import java.time.LocalDateTime;

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
	private String workName;
	@NotNull
	private LocalDateTime startDate;
	@NotNull
	private LocalDateTime endDate;
	@Min(0)
	private Integer stock;

	public static LicenseImageCreateDto createForTest(String artistName, String workName, LocalDateTime startDate,
		LocalDateTime endDate, Integer stock) {
		LicenseImageCreateDto dto = new LicenseImageCreateDto();
		dto.artistName = artistName;
		dto.workName = workName;
		dto.startDate = startDate;
		dto.endDate = endDate;
		dto.stock = stock;
		return dto;
	}
}
