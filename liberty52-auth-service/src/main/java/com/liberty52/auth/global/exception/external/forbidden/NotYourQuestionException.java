package com.liberty52.auth.global.exception.external.forbidden;

import com.liberty52.common.exception.external.forbidden.NotYourResourceException;

public class NotYourQuestionException extends NotYourResourceException {
    public NotYourQuestionException(String id) {
        super("Question", id);
    }
}
