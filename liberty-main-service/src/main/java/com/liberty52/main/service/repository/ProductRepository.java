package com.liberty52.main.service.repository;

import com.liberty52.main.service.entity.Product;
import com.liberty52.main.service.entity.ProductState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {

    Optional<Product> findByName(String name);


    Page<Product> findByProductStateNot(ProductState productState, Pageable pageable);
}
