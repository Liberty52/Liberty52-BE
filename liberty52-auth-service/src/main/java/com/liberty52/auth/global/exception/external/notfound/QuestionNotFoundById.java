package com.liberty52.auth.global.exception.external.notfound;

public class QuestionNotFoundById extends ResourceNotFoundException {
    public QuestionNotFoundById(String questionId) {
        super("Question", "id", questionId);
    }
}
