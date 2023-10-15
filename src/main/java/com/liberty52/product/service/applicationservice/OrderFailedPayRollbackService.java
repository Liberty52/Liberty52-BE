package com.liberty52.product.service.applicationservice;

public interface OrderFailedPayRollbackService {
    void rollback(String orderId);
}
