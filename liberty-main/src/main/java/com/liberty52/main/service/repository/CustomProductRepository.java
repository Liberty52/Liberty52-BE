package com.liberty52.main.service.repository;

import com.liberty52.main.service.entity.CustomProduct;
import com.liberty52.main.service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomProductRepository extends JpaRepository<CustomProduct, String> {

    void deleteByProduct(Product product);

    List<CustomProduct> findByProduct(Product product);
}
