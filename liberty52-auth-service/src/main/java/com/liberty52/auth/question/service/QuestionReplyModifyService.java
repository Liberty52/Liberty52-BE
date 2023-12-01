package com.liberty52.auth.question.service;

import com.liberty52.auth.question.web.dto.QuestionReplyModifyRequestDto;

public interface QuestionReplyModifyService {
    void modifyQuestionReplyByAdmin(String writerId, String role, String questionReplyId, QuestionReplyModifyRequestDto dto);
}
