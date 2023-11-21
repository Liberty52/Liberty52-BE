package com.liberty52.main.service.entity.license;

import com.liberty52.main.service.entity.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "license_option")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LicenseOption {
    @Id
    private final String id = UUID.randomUUID().toString();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "licenseOption")
    private final List<LicenseOptionDetail> licenseOptionDetails = new ArrayList<>();
    @Column(nullable = false)
    private String name;

    @Builder
    private LicenseOption(String name) {
        this.name = name;
    }

    public static LicenseOption create(String name) {
        return builder()
                .name(name)
                .build();
    }

    public void associate(Product product) {
        this.product = product;
        this.product.addLicenseOption(this);
    }

    public void addDetail(LicenseOptionDetail licenseOptionDetail) {
        this.licenseOptionDetails.add(licenseOptionDetail);
    }

    public void modifyLicenseOption(String name) {
        this.name = name;
    }
}
