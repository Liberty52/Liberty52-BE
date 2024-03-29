package com.liberty52.main.global.event.handler;

import com.liberty52.main.global.adapter.mail.MailSender;
import com.liberty52.main.global.event.events.SendMailEvent;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class SendMailEventHandler {

    private final MailSender mailSender;

    @Async
    @EventListener(SendMailEvent.class)
    public void handlingSendMailEvent(SendMailEvent sendMailEvent) throws MessagingException {
        log.info("[LIB-LOG] {} Send Mail to {}.", sendMailEvent.getTitle(), sendMailEvent.getTo());
        MailSender.Mail mail = MailSender.buildMail(
                sendMailEvent.getTo(),
                sendMailEvent.getTitle(),
                sendMailEvent.getContent(),
                sendMailEvent.isUseHtml()
        );
        mailSender.prepareAndSend(mail);
    }


}
