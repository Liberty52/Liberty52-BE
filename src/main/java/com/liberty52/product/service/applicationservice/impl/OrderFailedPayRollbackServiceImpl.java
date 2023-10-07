package com.liberty52.product.service.applicationservice.impl;

import com.liberty52.product.service.applicationservice.OrderFailedPayRollbackService;
import com.liberty52.product.service.entity.CustomProductOption;
import com.liberty52.product.service.entity.Orders;
import com.liberty52.product.service.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderFailedPayRollbackServiceImpl implements OrderFailedPayRollbackService {

    private final OrdersRepository ordersRepository;

    @Override
    @Transactional
    public void rollback(String orderId) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(RuntimeException::new);

        order.getCustomProducts().forEach(cp -> {
            cp.getOptions().stream()
                    .map(CustomProductOption::getOptionDetail)
                    .forEach(it -> it.rollbackStock(cp.getQuantity()));
        });
    }
}
