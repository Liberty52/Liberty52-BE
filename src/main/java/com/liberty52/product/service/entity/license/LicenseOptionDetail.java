package com.liberty52.product.service.entity.license;

import java.time.LocalDate;
import java.util.UUID;

import com.liberty52.product.service.controller.dto.LicenseOptionDetailModifyDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
	@JoinColumn(name = "license_option_id")
	private LicenseOption licenseOption;

	@OneToOne(mappedBy = "licenseOptionDetail" , cascade = CascadeType.ALL)
	private CustomLicenseOption customLicenseOption;
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
	@Column(nullable = false)
	private Integer price;
	@Column(nullable = false)
	private LocalDate startDate;
	@Column(nullable = false)
	private LocalDate endDate;

	@Builder
	private LicenseOptionDetail(String artName, String artistName, Integer stock, Boolean onSale, String artUrl,
		Integer price, LocalDate startDate, LocalDate endDate) {
		this.artName = artName;
		this.artistName = artistName;
		this.stock = stock;
		this.onSale = onSale;
		this.artUrl = artUrl;
		this.price = price;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public static LicenseOptionDetail create(String artName, String artistName, Integer stock, Boolean onSale,
		String artUrl, Integer price, LocalDate startDate, LocalDate endDate) {
		return builder().artName(artName).artistName(artistName).stock(stock).onSale(onSale).artUrl(artUrl)
			.price(price).startDate(startDate).endDate(endDate).build();
	}

	public void associate(LicenseOption licenseOption) {
		this.licenseOption = licenseOption;
		this.licenseOption.addDetail(this);
	}

	public void associate(CustomLicenseOption customLicenseOption) {
		this.customLicenseOption = customLicenseOption;
	}

	public void modifyLicenseOptionDetail(LicenseOptionDetailModifyDto dto) {
		this.artName = dto.getArtName();
		this.artistName = dto.getArtistName();
		this.stock = dto.getStock();
		this.onSale = dto.getOnSale();
		this.price = dto.getPrice();
		this.startDate = dto.getStartDate();
		this.endDate = dto.getEndDate();
	}

	public void modifyLicenseArtUrl(String artUrl) {
		this.artUrl = artUrl;
	}

	public void updateOnSale() {
		onSale = !onSale;
	}

}
