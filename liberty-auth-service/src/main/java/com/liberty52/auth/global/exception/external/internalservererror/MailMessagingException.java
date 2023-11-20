package com.liberty52.auth.global.exception.external.internalservererror;

public class MailMessagingException extends InternalServerErrorException {
    public MailMessagingException() {
        super("현재 시스템에 오류가 발생하였습니다. 다시 시도해주세요.\n자세한 문의는 고객센터에 문의해주세요.");
    }
}
