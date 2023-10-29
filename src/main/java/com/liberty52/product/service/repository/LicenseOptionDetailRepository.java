package com.liberty52.product.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.liberty52.product.service.entity.license.LicenseOptionDetail;

public interface LicenseOptionDetailRepository extends JpaRepository<LicenseOptionDetail, String> {
}