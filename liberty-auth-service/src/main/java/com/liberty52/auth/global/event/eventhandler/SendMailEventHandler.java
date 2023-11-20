package com.liberty52.auth.global.event.eventhandler;

import com.liberty52.auth.global.adapter.MailSender;
import com.liberty52.auth.global.event.events.SendMailEvent;
import com.liberty52.auth.global.event.events.SignedUpEvent;
import com.liberty52.auth.global.utils.EmailPageFormatter;
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
    @EventListener(SignedUpEvent.class)
    public void handlingSendMailEvent(SendMailEvent sendMailEvent) throws MessagingException {
        MailSender.Mail mail = MailSender.buildMail(sendMailEvent.getEmail(), sendMailEvent.getTitle(),
                EmailPageFormatter.formatSignUpOrDeletePage(sendMailEvent.getMainText(), sendMailEvent.getSubText(),
                        sendMailEvent.getName(), sendMailEvent.getEmail(), sendMailEvent.getDateText(), sendMailEvent.getDate()), true);
        mailSender.prepare(mail);
        mailSender.send();
    }

}
