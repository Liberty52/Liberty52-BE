package com.liberty52.product.service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.liberty52.product.service.entity.license.LicenseOption;

public interface LicenseOptionRepository extends JpaRepository<LicenseOption, String> {
	Optional<LicenseOption> findByName(String name);
	Optional<LicenseOption> findByProductId(String productId);
}