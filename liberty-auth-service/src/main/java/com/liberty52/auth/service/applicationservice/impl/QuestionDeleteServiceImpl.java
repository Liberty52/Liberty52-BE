package com.liberty52.auth.service.applicationservice.impl;

import com.liberty52.auth.global.exception.external.forbidden.NotYourQuestionException;
import com.liberty52.auth.global.exception.external.notfound.QuestionNotFoundById;
import com.liberty52.auth.service.applicationservice.QuestionDeleteService;
import com.liberty52.auth.service.entity.Question;
import com.liberty52.auth.service.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionDeleteServiceImpl implements QuestionDeleteService {

    private final QuestionRepository questionRepository;

    @Override
    public void deleteQuestion(String writerId, String questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new QuestionNotFoundById(questionId));
        if(!writerId.equals(question.getWriterId())) {
            throw new NotYourQuestionException(writerId);
        }
        questionRepository.delete(question);
    }

    @Override
    public void deleteAllQuestion(String writerId) {
        questionRepository.deleteAllByWriterId(writerId);
    }
}
