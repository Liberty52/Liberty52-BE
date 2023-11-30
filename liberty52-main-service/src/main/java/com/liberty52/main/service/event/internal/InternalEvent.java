package com.liberty52.main.service.event.internal;

import com.liberty52.main.service.event.Event;

public interface InternalEvent<T> extends Event {
    T getBody();
}
