package com.liberty52.product.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.liberty52.product.service.entity.license.LicenseOption;

public interface LicenseOptionRepository extends JpaRepository<LicenseOption, String> {
}