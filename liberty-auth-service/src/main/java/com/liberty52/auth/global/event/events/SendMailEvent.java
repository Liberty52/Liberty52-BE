package com.liberty52.auth.global.event.events;

import com.liberty52.auth.global.event.Event;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SendMailEvent implements Event {

    private String email;
    private String name;
    private String dateText;
    private String date;
    private String title;
    private String mainText;
    private String subText;

    public SendMailEvent(String title, String email, String name, String dateText, String mainText, String subText) {
        this.title = title;
        this.email = email;
        this.date = LocalDate.now().toString();
        this.name = name;
        this.dateText = dateText;
        this.mainText = mainText;
        this.subText = subText;
    }
}
