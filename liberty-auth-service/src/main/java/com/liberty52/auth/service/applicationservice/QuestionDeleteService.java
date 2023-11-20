package com.liberty52.auth.service.applicationservice;

public interface QuestionDeleteService {
    void deleteQuestion(String writerId, String questionId);

    void deleteAllQuestion(String writerId);
}
