package com.liberty52.auth.question.service;

import com.liberty52.auth.global.exception.external.forbidden.NotYourQuestionException;
import com.liberty52.auth.global.exception.external.internalservererror.InvalidResourceConstraintException;
import com.liberty52.auth.global.exception.external.notfound.QuestionNotFoundById;
import com.liberty52.auth.global.exception.internal.InvalidQuestionContentException;
import com.liberty52.auth.global.exception.internal.InvalidQuestionTitleException;
import com.liberty52.auth.question.entity.Question;
import com.liberty52.auth.question.repository.QuestionRepository;
import com.liberty52.auth.question.web.dto.QuestionModifyRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionModifyServiceImpl implements QuestionModifyService {
    private final QuestionRepository questionRepository;

    @Override
    public void modifyQuestion(String writerId, String questionId, QuestionModifyRequestDto dto) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new QuestionNotFoundById(questionId));
        if(!writerId.equals(question.getWriterId()))
            throw new NotYourQuestionException(writerId);
        try {
            question.modify(dto.getTitle(), dto.getContent());
        } catch (InvalidQuestionContentException | InvalidQuestionTitleException e) {
            throw new InvalidResourceConstraintException(e);
        }
    }
}
