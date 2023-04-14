package com.liberty52.product.service.applicationservice;

import com.liberty52.product.service.controller.dto.OrdersRetrieveResponse;
import com.liberty52.product.service.repository.OrderQueryDslRepository;
import com.liberty52.product.service.repository.OrdersRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderRetrieveServiceImpl implements
        OrderRetrieveService {

    private final OrderQueryDslRepository orderQueryDslRepository;

    @Override
    public List<OrdersRetrieveResponse> retrieveOrders(String authId) {

        return orderQueryDslRepository.retrieveOrders(authId);
    }
}
