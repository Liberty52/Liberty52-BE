package com.liberty52.auth.question.service;

public interface QuestionDeleteService {
    void deleteQuestion(String writerId, String questionId);

    void deleteAllQuestion(String writerId);
}
