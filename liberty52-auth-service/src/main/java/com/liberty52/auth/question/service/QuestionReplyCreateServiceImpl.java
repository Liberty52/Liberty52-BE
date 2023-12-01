package com.liberty52.auth.question.service;

import com.liberty52.auth.global.exception.external.notfound.QuestionNotFoundById;
import com.liberty52.auth.global.utils.AdminRoleUtils;
import com.liberty52.auth.question.entity.Question;
import com.liberty52.auth.question.entity.QuestionReply;
import com.liberty52.auth.question.repository.QuestionRepository;
import com.liberty52.auth.question.web.dto.QuestionReplyCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionReplyCreateServiceImpl implements QuestionReplyCreateService {

  private final QuestionRepository questionRepository;

  @Override
  public void createQuestionReplyByAdmin(String adminId, String role, QuestionReplyCreateRequestDto dto) {
    AdminRoleUtils.isAdmin(role);
    Question question = questionRepository.findById(dto.getQuestionId())
        .orElseThrow(() -> new QuestionNotFoundById(dto.getQuestionId()));
    QuestionReply questionReply = QuestionReply.create(dto.getContent(), adminId);
    questionReply.associate(question);
  }
}
