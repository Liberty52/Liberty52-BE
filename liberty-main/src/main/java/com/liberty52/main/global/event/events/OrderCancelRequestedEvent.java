package com.liberty52.main.global.event.events;

import com.liberty52.main.global.adapter.mail.MailContentMaker;
import com.liberty52.main.global.adapter.mail.config.MailConstants;
import com.liberty52.main.service.entity.Orders;
import com.liberty52.main.service.event.Event;

public class OrderCancelRequestedEvent extends SendMailEvent implements Event {

    private static final String CONTENT_TITLE = "주문 취소가 요청되었습니다.";

    public OrderCancelRequestedEvent(String to, String title, String content, boolean isUseHtml) {
        super(to, title, content, isUseHtml);
    }

    public static OrderCancelRequestedEvent toAdmin(Orders order, String adminEmailAddr) {
        return new OrderCancelRequestedEvent(adminEmailAddr,
                MailConstants.Title.Admin.ORDER_CANCEL_REQUESTED(order.getOrderDestination().getReceiverName()),
                MailContentMaker.makeOrderCanceledContent(CONTENT_TITLE, order),
                true);
    }
}
