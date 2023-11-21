package com.liberty52.auth.question.service;

import com.liberty52.auth.question.entity.Question;
import com.liberty52.auth.question.repository.QuestionRepository;
import com.liberty52.auth.question.web.dto.QuestionCreateRequestDto;
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
