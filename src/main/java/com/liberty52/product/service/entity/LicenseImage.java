package com.liberty52.product.service.entity;

import java.time.LocalDate;
import java.util.UUID;

import com.liberty52.product.service.controller.dto.LicenseImageModifyDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "license_image")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LicenseImage {
	@Id
	private String id = UUID.randomUUID().toString();
	@Column(nullable = false)
	private String artistName;
	@Column(nullable = false)
	private String artName;
	@Column(nullable = false)
	private LocalDate startDate;
	@Column(nullable = false)
	private LocalDate endDate;
	@Column(nullable = false)
	private String licenseImageUrl;
	@Column(nullable = false)
	private Integer stock;

	@Builder
	private LicenseImage(String artistName, String artName, LocalDate startDate, LocalDate endDate,
		String licenseImageUrl, Integer stock) {
		this.artistName = artistName;
		this.artName = artName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.licenseImageUrl = licenseImageUrl;
		this.stock = stock;
	}

	public void updateLicense(LicenseImageModifyDto dto) {
		this.artistName = dto.getArtistName();
		this.artName = dto.getArtName();
		this.startDate = dto.getStartDate();
		this.endDate = dto.getEndDate();
		this.stock = dto.getStock();
	}

	public void updateLicenseImageUrl(String licenseImageUrl) {
		this.licenseImageUrl = licenseImageUrl;
	}
}
