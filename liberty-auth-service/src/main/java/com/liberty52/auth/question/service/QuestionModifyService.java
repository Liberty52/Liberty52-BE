package com.liberty52.auth.question.service;

import com.liberty52.auth.question.web.dto.QuestionModifyRequestDto;

public interface QuestionModifyService {
    void modifyQuestion(String writerId, String questionId, QuestionModifyRequestDto dto);
}
