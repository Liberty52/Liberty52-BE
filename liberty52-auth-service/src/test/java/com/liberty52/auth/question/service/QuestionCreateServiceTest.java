package com.liberty52.auth.question.service;

import com.liberty52.auth.question.entity.Question;
import com.liberty52.auth.question.entity.QuestionStatus;
import com.liberty52.auth.question.repository.QuestionRepository;
import com.liberty52.auth.question.web.dto.QuestionCreateRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class QuestionCreateServiceTest {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    QuestionCreateService questionCreateService;

    @Test
    void 문의추가() {
        String writerId = "testId";
        QuestionCreateRequestDto dto = QuestionCreateRequestDto.create("제목", "내용");
        questionCreateService.createQuestion(writerId, dto);

        Question question = questionRepository.findByWriterId(writerId).get(0);
        assertThat(question.getTitle()).isEqualTo("제목");
        assertThat(question.getContent()).isEqualTo("내용");
        assertThat(question.getWriterId()).isEqualTo(writerId);
        assertThat(question.getStatus()).isEqualTo(QuestionStatus.WAITING);
        assertThat(question.getCreatedAt().toLocalDate()).isEqualTo(LocalDate.now());
    }
}
