package com.liberty52.main.global.event.events;

import com.liberty52.main.global.adapter.mail.MailContentMaker;
import com.liberty52.main.global.adapter.mail.config.MailConstants;
import com.liberty52.main.service.entity.Orders;
import com.liberty52.main.service.event.Event;

public class CardOrderedCompletedEvent extends SendMailEvent implements Event {

    public CardOrderedCompletedEvent(Orders order) {
        this(order.getOrderDestination().getReceiverEmail(),
                MailConstants.Title.Customer.CARD_ORDERED_COMPLETED,
                MailContentMaker.makeCardOrderedCompletedContent(order),
                true);
    }

    public CardOrderedCompletedEvent(String to, String title, String content, boolean isUseHtml) {
        super(to, title, content, isUseHtml);
    }
}
