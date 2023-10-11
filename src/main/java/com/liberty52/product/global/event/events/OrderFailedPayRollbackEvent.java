package com.liberty52.product.global.event.events;

import com.liberty52.product.service.event.Event;

public record OrderFailedPayRollbackEvent(String orderId) implements Event {
}
