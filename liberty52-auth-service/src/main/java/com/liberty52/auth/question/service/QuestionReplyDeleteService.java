package com.liberty52.auth.question.service;

public interface QuestionReplyDeleteService {
    void deleteQuestionReplyByAdmin(String adminId, String role, String questionReplyId);
}
