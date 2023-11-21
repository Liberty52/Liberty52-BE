package com.liberty52.auth.question.service;

import com.liberty52.auth.question.web.dto.QuestionReplyCreateRequestDto;

public interface QuestionReplyCreateService {
    void createQuestionReplyByAdmin(String adminId, String role, QuestionReplyCreateRequestDto dto);
}
