package com.liberty52.product.service.repository;

import com.liberty52.product.service.controller.dto.OrdersRetrieveResponse;
import java.util.List;

public interface OrderQueryDslRepository {

    List<OrdersRetrieveResponse> retrieveOrders(String authId);

}
