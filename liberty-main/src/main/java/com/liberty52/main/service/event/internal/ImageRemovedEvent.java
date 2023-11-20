package com.liberty52.main.service.event.internal;

import com.liberty52.main.service.event.internal.dto.ImageRemovedEventDto;

public class ImageRemovedEvent extends InternalEventBase<ImageRemovedEventDto> {
    public ImageRemovedEvent(Object source, ImageRemovedEventDto body) {
        super(source, body);
    }
}
