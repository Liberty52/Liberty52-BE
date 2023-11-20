package com.liberty52.main.service.repository;

import com.liberty52.main.service.entity.CanceledOrders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CanceledOrdersRepository extends JpaRepository<CanceledOrders, String> {
}
