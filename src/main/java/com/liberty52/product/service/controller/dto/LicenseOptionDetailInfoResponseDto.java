package com.liberty52.product.service.controller.dto;

import com.liberty52.product.service.entity.license.LicenseOptionDetail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LicenseOptionDetailInfoResponseDto {

	String licenseOptionDetailId;
	String artName;
	String artistName;
	Integer stock;
	Boolean onSale;
	String artUrl;

	public LicenseOptionDetailInfoResponseDto(LicenseOptionDetail licenseOptionDetail) {
		licenseOptionDetailId = licenseOptionDetail.getId();
		artName = licenseOptionDetail.getArtName();
		artistName = licenseOptionDetail.getArtistName();
		stock = licenseOptionDetail.getStock();
		onSale = licenseOptionDetail.getOnSale();
		artUrl = licenseOptionDetail.getArtUrl();
	}

}
