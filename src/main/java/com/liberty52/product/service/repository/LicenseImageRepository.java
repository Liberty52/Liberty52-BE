package com.liberty52.product.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.liberty52.product.service.entity.LicenseImage;

public interface LicenseImageRepository extends JpaRepository<LicenseImage, String> {
}
