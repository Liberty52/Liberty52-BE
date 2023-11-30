package com.liberty52.auth.question.service;

import com.liberty52.auth.question.web.dto.QuestionCreateRequestDto;

public interface QuestionCreateService {
    void createQuestion(String writerId, QuestionCreateRequestDto dto);
}
