package com.liberty52.auth.service.applicationservice;

import com.liberty52.auth.service.controller.dto.QuestionCreateRequestDto;

public interface QuestionCreateService {
    void createQuestion(String writerId, QuestionCreateRequestDto dto);
}
