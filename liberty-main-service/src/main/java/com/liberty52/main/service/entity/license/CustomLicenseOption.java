package com.liberty52.main.service.entity.license;

import com.liberty52.main.service.entity.CustomProduct;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

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

    @OneToOne(mappedBy = "customLicenseOption")
    private CustomProduct customProduct;

    public static CustomLicenseOption create() {
        return new CustomLicenseOption();
    }

    public void associate(LicenseOptionDetail licenseOptionDetail) {
        this.licenseOptionDetail = licenseOptionDetail;
    }

    public void associate(CustomProduct customProduct) {
        this.customProduct = customProduct;
        this.customProduct.associateWithCustomLicenseOption(this);
    }
}
