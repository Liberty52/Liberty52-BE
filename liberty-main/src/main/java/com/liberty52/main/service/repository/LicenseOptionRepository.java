package com.liberty52.main.service.repository;

import com.liberty52.main.service.entity.license.LicenseOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LicenseOptionRepository extends JpaRepository<LicenseOption, String> {
    Optional<LicenseOption> findByName(String name);

    Optional<LicenseOption> findByProductId(String productId);
}