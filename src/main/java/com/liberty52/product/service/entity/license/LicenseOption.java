package com.liberty52.product.service.entity.license;

import java.util.List;
import java.util.UUID;

import com.liberty52.product.service.entity.Product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "license_option")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LicenseOption {
	@Id
	private String id = UUID.randomUUID().toString();
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@OneToMany(mappedBy = "licenseOption")
	private List<LicenseOptionDetail> licenseOptionDetails;
	@Column(nullable = false)
	private String name;

	public void associate(Product product) {
		this.product = product;
		this.product.addLicenseOptions(this);
	}

	public LicenseOption(String name) {
		this.name = name;
	}
	public void addDetail(LicenseOptionDetail licenseOptionDetail) {
		this.licenseOptionDetails.add(licenseOptionDetail);
	}
}
