package com.liberty52.product.service.repository;

import com.liberty52.product.service.entity.CustomProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCartOptionRepository extends JpaRepository<CustomProductOption, String> {
}