package com.liberty52.main.service.repository;

import com.liberty52.main.service.entity.CustomProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomProductOptionRepository extends JpaRepository<CustomProductOption, String> {
}