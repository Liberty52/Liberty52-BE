package com.liberty52.product.service.controller.dto;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.liberty52.product.service.entity.license.LicenseOption;
import com.liberty52.product.service.entity.license.LicenseOptionDetail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LicenseOptionInfoResponseDto {

	String licenseOptionId;
	String optionName;
	Boolean onSale;
	Boolean require;
	List<LicenseOptionDetailInfoResponseDto> licenseOptionDetailList;

	public LicenseOptionInfoResponseDto(LicenseOption licenseOption, boolean onSale) {
		this.licenseOptionId = licenseOption.getId();
		this.optionName = licenseOption.getName();
		this.require = licenseOption.getRequire();
		this.onSale = licenseOption.getOnSale();

		if (licenseOption.getLicenseOptionDetails().isEmpty()) {
			licenseOptionDetailList = Collections.emptyList();
		}

		if (onSale) {
			licenseOptionDetailList = licenseOption.getLicenseOptionDetails()
				.stream()
				.filter(LicenseOptionDetail::getOnSale)
				.map(LicenseOptionDetailInfoResponseDto::new)
				.toList();

		} else {
			licenseOptionDetailList = licenseOption.getLicenseOptionDetails()
				.stream()
				.sorted(Comparator.comparing(LicenseOptionDetail::getOnSale).reversed())
				.map(LicenseOptionDetailInfoResponseDto::new)
				.toList();
		}
	}

}
