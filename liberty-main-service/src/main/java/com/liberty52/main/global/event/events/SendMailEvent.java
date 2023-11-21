package com.liberty52.main.global.event.events;

import com.liberty52.main.service.event.Event;
import lombok.Getter;

@Getter
public class SendMailEvent implements Event {

    private final String to;
    private final String title;
    private final String content;
    private final boolean isUseHtml;

    public SendMailEvent(String to, String title, String content, boolean isUseHtml) {
        this.to = to;
        this.title = title;
        this.content = content;
        this.isUseHtml = isUseHtml;
    }

}
