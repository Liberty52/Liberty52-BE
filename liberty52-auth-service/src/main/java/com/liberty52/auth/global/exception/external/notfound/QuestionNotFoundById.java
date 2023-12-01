package com.liberty52.auth.global.exception.external.notfound;

import com.liberty52.common.exception.external.notfound.ResourceNotFoundException;

public class QuestionNotFoundById extends ResourceNotFoundException {
    public QuestionNotFoundById(String questionId) {
        super("Question", "id", questionId);
    }
}
