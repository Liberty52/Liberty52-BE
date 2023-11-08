package com.liberty52.product.service.entity.license;

import java.util.UUID;

import com.liberty52.product.service.entity.CustomProduct;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "custom_license_option")
@Getter
@NoArgsConstructor
public class CustomLicenseOption {
	@Id
	private String id = UUID.randomUUID().toString();

	@OneToOne
	@JoinColumn(name = "license_option_detail_id")
	private LicenseOptionDetail licenseOptionDetail;

	@OneToOne(mappedBy = "customLicenseOption" , cascade = CascadeType.PERSIST)
	private CustomProduct customProduct;

	public void associate(LicenseOptionDetail licenseOptionDetail) {
		this.licenseOptionDetail = licenseOptionDetail;
		this.licenseOptionDetail.associate(this);
	}

	public void associate(CustomProduct customProduct) {
		this.customProduct = customProduct;
		this.customProduct.associateWithCustomLicenseOption(this);
	}

	public static CustomLicenseOption create(){
		return new CustomLicenseOption();
	}
}
