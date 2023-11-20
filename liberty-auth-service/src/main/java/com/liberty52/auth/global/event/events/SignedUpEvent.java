package com.liberty52.auth.global.event.events;

import com.liberty52.auth.global.constants.EmailConstants;
import com.liberty52.auth.global.event.Event;

public class SignedUpEvent extends SendMailEvent implements Event {

    public SignedUpEvent(String email, String name) {
        super(EmailConstants.SignUpEmailConstants.SIGN_UP_TITLE,email, name, EmailConstants.SignUpEmailConstants.SIGN_UP_DATE_TEXT, EmailConstants.SignUpEmailConstants.SIGN_UP_MAIN_TEXT, EmailConstants.SignUpEmailConstants.SIGN_UP_SUB_TEXT );
    }
}
