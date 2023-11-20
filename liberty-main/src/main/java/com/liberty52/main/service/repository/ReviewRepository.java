package com.liberty52.main.service.repository;

import com.liberty52.main.service.entity.CustomProduct;
import com.liberty52.main.service.entity.Product;
import com.liberty52.main.service.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, String> {
    Optional<Review> findByCustomProduct(CustomProduct customProduct);

    List<Review> findByCustomProduct_Product(Product product);
}