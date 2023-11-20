package com.liberty52.auth.service.applicationservice.impl;

import com.liberty52.auth.service.applicationservice.QuestionCreateService;
import com.liberty52.auth.service.controller.dto.QuestionCreateRequestDto;
import com.liberty52.auth.service.entity.Question;
import com.liberty52.auth.service.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionCreateServiceImpl implements QuestionCreateService {

    private final QuestionRepository questionRepository;

    @Override
    public void createQuestion(String writerId, QuestionCreateRequestDto dto) {
        Question question = Question.create(dto.getTitle(), dto.getContent(), writerId);
        questionRepository.save(question);
    }
}
