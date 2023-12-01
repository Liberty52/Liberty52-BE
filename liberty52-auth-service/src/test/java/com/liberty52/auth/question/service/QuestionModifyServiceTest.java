package com.liberty52.auth.question.service;

import com.liberty52.auth.global.exception.external.forbidden.NotYourQuestionException;
import com.liberty52.auth.global.exception.external.internalservererror.InvalidResourceConstraintException;
import com.liberty52.auth.global.exception.external.notfound.QuestionNotFoundById;
import com.liberty52.auth.global.exception.internal.InvalidQuestionContentException;
import com.liberty52.auth.global.exception.internal.InvalidQuestionTitleException;
import com.liberty52.auth.question.entity.Question;
import com.liberty52.auth.question.repository.QuestionRepository;
import com.liberty52.auth.question.web.dto.QuestionCreateRequestDto;
import com.liberty52.auth.question.web.dto.QuestionModifyRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Transactional
@SpringBootTest
public class QuestionModifyServiceTest {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    QuestionModifyService questionModifyService;

    @Autowired
    QuestionCreateService questionCreateService;

    Question question;
    String writerId = "testId";

    @BeforeEach
    void beforeEach() {
        String title = "제목";
        String content = "내용";
        questionCreateService.createQuestion(writerId, QuestionCreateRequestDto.create(title, content));
        question = questionRepository.findByWriterId(writerId).get(0);
    }

    @Test
    void modify() {
        String mTitle = "modified";
        String mContent = "modified content";
        questionModifyService.modifyQuestion(writerId, question.getId(), QuestionModifyRequestDto.createForTest(mTitle, mContent));

        // then
        question = questionRepository.findByWriterId(writerId).get(0);
        Assertions.assertEquals(mTitle, question.getTitle());
        Assertions.assertEquals(mContent, question.getContent());
    }

    @Test
    void throwNotYourQuestion() {
        String strangeId = UUID.randomUUID().toString();
        Assertions.assertThrows(
                NotYourQuestionException.class,
                () -> questionModifyService.modifyQuestion(strangeId, question.getId(), createDto()));
    }

    @Test
    void throwQuestionNotFoundById() {
        String strangeId = UUID.randomUUID().toString();
        Assertions.assertThrows(
                QuestionNotFoundById.class,
                () -> questionModifyService.modifyQuestion(writerId, strangeId, createDto()));
    }

    @Test
    void throwInvalidResourceConstraintException() {
        InvalidResourceConstraintException e = Assertions.assertThrows(
                InvalidResourceConstraintException.class,
                () -> questionModifyService.modifyQuestion(writerId, question.getId(), createDto("?".repeat(Question.TITLE_MIN_LENGTH - 1), "?".repeat(Question.CONTENT_MIN_LENGTH)))
        );
        Assertions.assertTrue(e.getErrorMessage().contains(InvalidQuestionTitleException.class.getSimpleName()));

        e  = Assertions.assertThrows(
                InvalidResourceConstraintException.class,
                () -> questionModifyService.modifyQuestion(writerId, question.getId(), createDto("?".repeat(Question.TITLE_MAX_LENGTH + 1), "?".repeat(Question.CONTENT_MIN_LENGTH)))
        );
        Assertions.assertTrue(e.getErrorMessage().contains(InvalidQuestionTitleException.class.getSimpleName()));

        e = Assertions.assertThrows(
                InvalidResourceConstraintException.class,
                () -> questionModifyService.modifyQuestion(writerId, question.getId(), createDto("?".repeat(Question.TITLE_MIN_LENGTH), "?".repeat(Question.CONTENT_MIN_LENGTH - 1)))
        );
        Assertions.assertTrue(e.getErrorMessage().contains(InvalidQuestionContentException.class.getSimpleName()));

        e  = Assertions.assertThrows(
                InvalidResourceConstraintException.class,
                () -> questionModifyService.modifyQuestion(writerId, question.getId(), createDto("?".repeat(Question.TITLE_MIN_LENGTH), "?".repeat(Question.CONTENT_MAX_LENGTH + 1)))
        );
        Assertions.assertTrue(e.getErrorMessage().contains(InvalidQuestionContentException.class.getSimpleName()));
    }

    QuestionModifyRequestDto createDto() {
        return createDto("?".repeat(Question.TITLE_MIN_LENGTH), "?".repeat(Question.CONTENT_MIN_LENGTH));
    }

    QuestionModifyRequestDto createDto(String title, String content) {
        return QuestionModifyRequestDto.createForTest(title, content);
    }
}
