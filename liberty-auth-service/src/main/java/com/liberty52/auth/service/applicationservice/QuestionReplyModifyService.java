package com.liberty52.auth.service.applicationservice;

import com.liberty52.auth.service.controller.dto.QuestionReplyModifyRequestDto;

public interface QuestionReplyModifyService {
    void modifyQuestionReplyByAdmin(String writerId, String role, String questionReplyId, QuestionReplyModifyRequestDto dto);
}
