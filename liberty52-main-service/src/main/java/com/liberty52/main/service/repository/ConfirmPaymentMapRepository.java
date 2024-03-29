package com.liberty52.main.service.repository;

import com.liberty52.main.global.exception.external.internalservererror.ConfirmPaymentException;
import com.liberty52.main.service.entity.Orders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class ConfirmPaymentMapRepository {

    private final ConcurrentHashMap<String, Orders> map = new ConcurrentHashMap<>();

    public boolean containsOrderId(String orderId) {
        return map.containsKey(orderId);
    }

    public Orders get(String orderId) {
        return map.getOrDefault(orderId, null);
    }

    public Orders getAndRemove(String orderId) {
        Orders orders = map.remove(orderId);

        if (orders == null) {
            log.error("Map.get null 발생 - 요청 주문 ID: {}", orderId);
            throw new ConfirmPaymentException();
        }

        return orders;
    }

    public void put(String orderId, Orders order) {
        map.put(orderId, order);
    }

}
