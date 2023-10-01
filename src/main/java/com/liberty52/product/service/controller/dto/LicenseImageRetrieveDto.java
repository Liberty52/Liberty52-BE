package com.liberty52.product.service.controller.dto;

import java.time.LocalDate;

import com.liberty52.product.global.util.Utils;
import com.liberty52.product.service.entity.LicenseImage;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LicenseImageRetrieveDto {
	private String id;
	private String artistName;
	private String workName;
	private LocalDate startDate;
	private LocalDate endDate;
	private String licenseImageUrl;
	private Integer stock;

	public LicenseImageRetrieveDto(LicenseImage licenseImage) {
		this.id = licenseImage.getId();
		this.artistName = licenseImage.getArtistName();
		this.workName = licenseImage.getWorkName();
		this.startDate = licenseImage.getStartDate();
		this.endDate = licenseImage.getEndDate();
		this.licenseImageUrl = licenseImage.getLicenseImageUrl();
		this.stock = licenseImage.getStock();
	}
}
