package com.liberty52.main.service.repository;

import com.liberty52.main.service.controller.dto.SalesRequestDto;
import com.liberty52.main.service.entity.OrderStatus;
import com.liberty52.main.service.entity.Orders;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OrderQueryDslRepository {

    List<Orders> retrieveOrders(String authId);

    Optional<Orders> retrieveOrderDetail(String authId, String orderId);

    Optional<Orders> retrieveGuestOrderDetail(String authId, String orderNumber);

    List<Orders> retrieveOrdersByAdmin(Pageable pageable);

    Optional<Orders> retrieveOrderDetailByOrderId(String orderId);

    List<Orders> retrieveCanceledOrdersByAdmin(Pageable pageable);

    List<Orders> retrieveOnlyRequestedCanceledOrdersByAdmin(Pageable pageable);

    List<Orders> retrieveOnlyCanceledOrdersByAdmin(Pageable pageable);

    Optional<Orders> retrieveOrderDetailWithCanceledOrdersByAdmin(String orderId);

    OrderQueryDslRepositoryImpl.PageInfo getPageInfo(Pageable pageable);

    OrderQueryDslRepositoryImpl.PageInfo getCanceledOrdersPageInfo(Pageable pageable, OrderStatus... statuses);

    List<Tuple> retrieveByConditions(SalesRequestDto salesRequestDto);
}
