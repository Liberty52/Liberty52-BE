package com.liberty52.product.service.entity.license;

import java.util.UUID;

import com.liberty52.product.service.controller.dto.LicenseOptionDetailModifyDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "license_option_detail")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LicenseOptionDetail {
	@Id
	private String id = UUID.randomUUID().toString();
	@ManyToOne
	private LicenseOption licenseOption;
	@Column(nullable = false)
	String artName;
	@Column(nullable = false)
	String artistName;
	@Column(nullable = false)
	Integer stock;
	@Column(nullable = false)
	Boolean onSale;
	@Column(nullable = false)
	String artUrl;

	@Builder
	private LicenseOptionDetail(String artName, String artistName, Integer stock, Boolean onSale, String artUrl) {
		this.artName = artName;
		this.artistName = artistName;
		this.stock = stock;
		this.onSale = onSale;
		this.artUrl = artUrl;
	}

	public static LicenseOptionDetail create(String artName, String artistName, Integer stock, Boolean onSale,
		String artUrl) {
		return builder().artName(artName).artistName(artistName).stock(stock).onSale(onSale).artUrl(artUrl).build();
	}

	public void associate(LicenseOption licenseOption) {
		this.licenseOption = licenseOption;
		this.licenseOption.addDetail(this);
	}

	public void modifyLicenseOptionDetail(LicenseOptionDetailModifyDto dto, String artUrl) {
		this.artName = dto.getArtName();
		this.artistName = dto.getArtistName();
		this.stock = dto.getStock();
		this.onSale = dto.getOnSale();
		this.artUrl = artUrl;
	}
	public void updateOnSale() {
		onSale = !onSale;
	}

}
