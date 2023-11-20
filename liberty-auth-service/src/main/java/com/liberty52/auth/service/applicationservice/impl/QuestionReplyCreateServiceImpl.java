package com.liberty52.auth.service.applicationservice.impl;

import com.liberty52.auth.global.exception.external.notfound.QuestionNotFoundById;
import com.liberty52.auth.global.utils.AdminRoleUtils;
import com.liberty52.auth.service.applicationservice.QuestionReplyCreateService;
import com.liberty52.auth.service.controller.dto.QuestionReplyCreateRequestDto;
import com.liberty52.auth.service.entity.Question;
import com.liberty52.auth.service.entity.QuestionReply;
import com.liberty52.auth.service.repository.QuestionRepository;
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
    AdminRoleUtils.checkRole(role);
    Question question = questionRepository.findById(dto.getQuestionId())
        .orElseThrow(() -> new QuestionNotFoundById(dto.getQuestionId()));
    QuestionReply questionReply = QuestionReply.create(dto.getContent(), adminId);
    questionReply.associate(question);
  }
}
