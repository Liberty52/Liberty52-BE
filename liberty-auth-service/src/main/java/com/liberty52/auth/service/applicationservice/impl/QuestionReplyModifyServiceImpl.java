package com.liberty52.auth.service.applicationservice.impl;

import com.liberty52.auth.global.exception.external.notfound.QuestionReplyNotFoundByIdException;
import com.liberty52.auth.global.utils.AdminRoleUtils;
import com.liberty52.auth.service.applicationservice.QuestionReplyModifyService;
import com.liberty52.auth.service.controller.dto.QuestionReplyModifyRequestDto;
import com.liberty52.auth.service.entity.QuestionReply;
import com.liberty52.auth.service.repository.QuestionReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionReplyModifyServiceImpl implements QuestionReplyModifyService {
    private final QuestionReplyRepository questionReplyRepository;

    @Override
    public void modifyQuestionReplyByAdmin(String writerId, String role, String questionReplyId, QuestionReplyModifyRequestDto dto) {
        AdminRoleUtils.checkRole(role);
        QuestionReply questionReply = questionReplyRepository.findById(questionReplyId)
                .orElseThrow(() -> new QuestionReplyNotFoundByIdException(questionReplyId));
        questionReply.modify(dto.getContent()); // ensure validated
    }
}
