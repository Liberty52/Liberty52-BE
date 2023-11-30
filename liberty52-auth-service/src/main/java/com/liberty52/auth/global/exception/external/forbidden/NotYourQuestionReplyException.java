package com.liberty52.auth.global.exception.external.forbidden;

import com.liberty52.auth.question.entity.QuestionReply;

public class NotYourQuestionReplyException extends NotYourResourceException {
    public NotYourQuestionReplyException(String writerId) {
        super(QuestionReply.class.getSimpleName(), writerId);
    }
}
