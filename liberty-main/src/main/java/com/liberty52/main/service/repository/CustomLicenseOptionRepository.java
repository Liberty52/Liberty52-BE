package com.liberty52.main.service.repository;

import com.liberty52.main.service.entity.license.CustomLicenseOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomLicenseOptionRepository extends JpaRepository<CustomLicenseOption, String> {

}
