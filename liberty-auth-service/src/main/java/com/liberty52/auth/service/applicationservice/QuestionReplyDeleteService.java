package com.liberty52.auth.service.applicationservice;

public interface QuestionReplyDeleteService {
    void deleteQuestionReplyByAdmin(String adminId, String role, String questionReplyId);
}
