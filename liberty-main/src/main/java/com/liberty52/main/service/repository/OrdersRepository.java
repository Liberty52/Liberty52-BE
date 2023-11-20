package com.liberty52.main.service.repository;

import com.liberty52.main.service.entity.OrderStatus;
import com.liberty52.main.service.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Orders, String> {

    List<Orders> findAllByOrderStatusAndOrderedAtLessThan(OrderStatus status, LocalDateTime orderedAt);

    Optional<Orders> findByOrderNum(String orderNumber);

}
