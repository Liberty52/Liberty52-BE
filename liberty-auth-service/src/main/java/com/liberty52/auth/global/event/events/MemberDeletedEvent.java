package com.liberty52.auth.global.event.events;

import com.liberty52.auth.global.constants.EmailConstants;
import com.liberty52.auth.global.event.Event;

public class MemberDeletedEvent extends SendMailEvent implements Event {

    private String authId;

    public MemberDeletedEvent(String email, String name, String authId) {
        super(EmailConstants.DeleteMemberEmailConstants.DELETE_MEMBER_TITLE, email, name, EmailConstants.DeleteMemberEmailConstants.DELETE_MEMBER_DATE_TEXT , EmailConstants.DeleteMemberEmailConstants.DELETE_MEMBER_MAIN_TEXT , EmailConstants.DeleteMemberEmailConstants.DELETE_MEMBER_SUB_TEXT);
        this.authId = authId;
    }

    public String getAuthId() {
        return authId;
    }
}
