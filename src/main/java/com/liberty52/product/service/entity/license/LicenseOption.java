package com.liberty52.product.service.entity.license;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.liberty52.product.service.entity.Product;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "license_option")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LicenseOption {
	@Id
	private String id = UUID.randomUUID().toString();
		@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "product_id")
	private Product product;

	@OneToMany(mappedBy = "licenseOption")
	private List<LicenseOptionDetail> licenseOptionDetails = new ArrayList<>();
	@Column(nullable = false)
	private String name;

	public void associate(Product product) {
		this.product = product;
		this.product.addLicenseOption(this);
	}

	@Builder
	private LicenseOption(String name) {
		this.name = name;
	}

	public static LicenseOption create(String name) {
		return builder()
			.name(name)
			.build();
	}

	public void addDetail(LicenseOptionDetail licenseOptionDetail) {
		this.licenseOptionDetails.add(licenseOptionDetail);
	}

	public void modifyLicenseOption(String name) {
		this.name = name;
	}
}
