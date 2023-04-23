package com.liberty52.product.service.repository;

import com.liberty52.product.service.entity.Orders;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class ConfirmPaymentMapRepository {

    private final ConcurrentHashMap<String, Orders> map = new ConcurrentHashMap<>();

    public boolean containsOrderId(String orderId) {
        return map.containsKey(orderId);
    }

    public Orders get(String orderId) {
        if(map.containsKey(orderId)) {
            return map.get(orderId);
        }
        return null;
    }

    public void put(String orderId, Orders order) {
        map.put(orderId, order);
    }

}
