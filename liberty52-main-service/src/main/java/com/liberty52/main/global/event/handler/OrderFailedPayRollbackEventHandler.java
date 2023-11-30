package com.liberty52.main.global.event.handler;

import com.liberty52.main.global.event.events.OrderFailedPayRollbackEvent;
import com.liberty52.main.service.applicationservice.OrderFailedPayRollbackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderFailedPayRollbackEventHandler {

    private final OrderFailedPayRollbackService service;

    @Async
    @EventListener(OrderFailedPayRollbackEvent.class)
    public void handleOrderFailedPayRollbackEvent(OrderFailedPayRollbackEvent event) {
        String orderId = event.orderId();
        service.rollback(orderId);
        log.info("Success rollback failed order and option's stock. orderId={}", orderId);
    }
}
