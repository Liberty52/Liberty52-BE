package com.liberty52.product.service.event.internal;

import com.liberty52.product.service.event.internal.dto.CustomProductRemovedEventDto;

public class CustomProductRemovedEvent extends InternalEventBase<CustomProductRemovedEventDto> {
    public CustomProductRemovedEvent(Object source, CustomProductRemovedEventDto body) {
        super(source, body);
    }
}
