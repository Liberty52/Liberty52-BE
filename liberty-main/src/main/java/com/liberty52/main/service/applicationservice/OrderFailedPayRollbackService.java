package com.liberty52.main.service.applicationservice;

public interface OrderFailedPayRollbackService {
    void rollback(String orderId);
}
