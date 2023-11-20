package com.liberty52.auth.global.exception.external.forbidden;

public class NotYourNoticeCommentException extends NotYourResourceException{
    public NotYourNoticeCommentException(String userId){
        super("NoticeComment", userId);
    }
}
