package com.liberty52.main.global.event;

import com.liberty52.main.service.event.Event;
import org.springframework.context.ApplicationEventPublisher;

public class Events {

    private static ApplicationEventPublisher publisher;

    static void setPublisher(ApplicationEventPublisher publisher) {
        Events.publisher = publisher;
    }

    public static void raise(Event event) {
        if (publisher == null)
            return;
        publisher.publishEvent(event);
    }

}
