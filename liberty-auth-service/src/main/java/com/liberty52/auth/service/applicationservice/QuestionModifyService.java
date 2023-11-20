package com.liberty52.auth.service.applicationservice;

import com.liberty52.auth.service.controller.dto.QuestionModifyRequestDto;

public interface QuestionModifyService {
    void modifyQuestion(String writerId, String questionId, QuestionModifyRequestDto dto);
}
