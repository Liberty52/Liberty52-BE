package com.liberty52.product.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.liberty52.product.service.entity.LicenseImage;

import java.time.LocalDate;
import java.util.List;

public interface LicenseImageRepository extends JpaRepository<LicenseImage, String> {
    List<LicenseImage> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(LocalDate today, LocalDate localDate);
}
