package com.liberty52.main.service.event.kafka;

import com.liberty52.main.service.event.Event;

public interface KafkaEvent<T> extends Event {
    String getTopic();

    T getBody();
}
