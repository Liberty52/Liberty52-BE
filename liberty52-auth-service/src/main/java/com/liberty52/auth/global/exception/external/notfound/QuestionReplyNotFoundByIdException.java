package com.liberty52.auth.global.exception.external.notfound;

import com.liberty52.auth.question.entity.QuestionReply;
import com.liberty52.common.exception.external.notfound.ResourceNotFoundException;

public class QuestionReplyNotFoundByIdException extends ResourceNotFoundException {
    public QuestionReplyNotFoundByIdException(String questionReplyId) {
        super(QuestionReply.class.getSimpleName(), "id", questionReplyId);
    }
}
