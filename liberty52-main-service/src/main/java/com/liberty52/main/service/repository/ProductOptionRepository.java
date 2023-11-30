package com.liberty52.main.service.repository;

import com.liberty52.main.service.entity.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductOptionRepository extends JpaRepository<ProductOption, String> {
    Optional<ProductOption> findByIdAndProduct_Id(String id, String productId);
}
