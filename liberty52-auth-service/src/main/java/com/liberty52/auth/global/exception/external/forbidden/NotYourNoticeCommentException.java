package com.liberty52.auth.global.exception.external.forbidden;

import com.liberty52.common.exception.external.forbidden.NotYourResourceException;

public class NotYourNoticeCommentException extends NotYourResourceException {
    public NotYourNoticeCommentException(String userId){
        super("NoticeComment", userId);
    }
}
