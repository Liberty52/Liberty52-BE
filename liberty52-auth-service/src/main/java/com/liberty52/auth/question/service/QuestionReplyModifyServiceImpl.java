package com.liberty52.auth.question.service;

import com.liberty52.auth.global.exception.external.notfound.QuestionReplyNotFoundByIdException;
import com.liberty52.auth.global.utils.AdminRoleUtils;
import com.liberty52.auth.question.entity.QuestionReply;
import com.liberty52.auth.question.repository.QuestionReplyRepository;
import com.liberty52.auth.question.web.dto.QuestionReplyModifyRequestDto;
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
        AdminRoleUtils.isAdmin(role);
        QuestionReply questionReply = questionReplyRepository.findById(questionReplyId)
                .orElseThrow(() -> new QuestionReplyNotFoundByIdException(questionReplyId));
        questionReply.modify(dto.getContent()); // ensure validated
    }
}
