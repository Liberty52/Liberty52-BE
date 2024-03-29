package com.liberty52.main.global.event.events;

import com.liberty52.main.global.adapter.mail.MailContentMaker;
import com.liberty52.main.global.adapter.mail.config.MailConstants;
import com.liberty52.main.service.entity.Orders;
import com.liberty52.main.service.event.Event;

public class OrderRequestDepositEvent extends SendMailEvent implements Event {

    public OrderRequestDepositEvent(Orders order) {
        this(order.getOrderDestination().getReceiverEmail(),
                MailConstants.Title.Customer.REQUEST_DEPOSIT,
                MailContentMaker.makeOrderRequestDepositContent(order),
                true);
    }

    public OrderRequestDepositEvent(String to, String title, String content, boolean isUseHtml) {
        super(to, title, content, isUseHtml);
    }

}
