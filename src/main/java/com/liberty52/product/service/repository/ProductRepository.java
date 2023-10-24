package com.liberty52.product.service.repository;

import com.liberty52.product.service.entity.Product;
import java.util.Optional;

import com.liberty52.product.service.entity.ProductState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {

  Optional<Product> findByName(String name);


  Page<Product> findByProductStateNot(ProductState productState, Pageable pageable);
}

