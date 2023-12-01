package com.liberty52.main.global.event.events;

import com.liberty52.main.service.event.Event;

public record OrderFailedPayRollbackEvent(String orderId) implements Event {
}
