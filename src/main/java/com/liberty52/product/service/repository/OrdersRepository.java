package com.liberty52.product.service.repository;

import com.liberty52.product.service.entity.Orders;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, String> {
  Optional<Orders> findByAuthId(String authId);
}
