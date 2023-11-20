package com.liberty52.main.service.repository;

import com.liberty52.main.service.entity.LicenseImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LicenseImageRepository extends JpaRepository<LicenseImage, String> {
    List<LicenseImage> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(LocalDate today, LocalDate localDate);
}
