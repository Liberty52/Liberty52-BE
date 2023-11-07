package com.liberty52.product.service.entity.license;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "license_product_option")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomLicenseOption {
	@Id
	private String id = UUID.randomUUID().toString();

	@OneToOne
	@JoinColumn(name = "license_option_detail_id")
	private LicenseOptionDetail licenseOptionDetail;

	public void associate(LicenseOptionDetail licenseOptionDetail) {
		this.licenseOptionDetail = licenseOptionDetail;
		this.licenseOptionDetail.associate(this);
	}
}
