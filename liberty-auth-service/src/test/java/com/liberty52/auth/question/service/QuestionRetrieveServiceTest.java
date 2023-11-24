package com.liberty52.auth.question.service;

import com.liberty52.auth.question.repository.QuestionRepository;
import com.liberty52.auth.question.web.dto.AdminQuestionRetrieveResponse;
import com.liberty52.auth.question.web.dto.QuestionDetailResponseDto;
import com.liberty52.auth.question.web.dto.QuestionReplyResponse;
import com.liberty52.auth.question.web.dto.QuestionRetrieveResponseDto;
import com.liberty52.auth.user.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class QuestionRetrieveServiceTest {

  private static final String WAITING = "WAITING";
  private static final String DONE = "DONE";
  private static final String writerId = "TESTER-001";
  private static final String questionedId = "QUESTION-001";
  @Autowired
  QuestionRepository questionRepository;

  @Autowired
  QuestionRetrieveService questionRetrieveService;

  @Test
  void retrieveQuestions() {
    QuestionRetrieveResponseDto response = questionRetrieveService.retrieveQuestions(writerId, 0, 5);
    assertThat(response.getCurrentPage()).isSameAs(1L);
    assertThat(response.getStartPage()).isSameAs(1L);
    assertThat(response.getLastPage()).isSameAs(1L);
    assertThat(response.getTotalPage()).isSameAs(1L);

    QuestionRetrieveResponseDto.QuestionContent questionContent = response.getContents().get(0);
    assertThat(questionContent.getStatus()).isEqualTo(DONE);
    assertThat(questionContent.getTitle()).isEqualTo("this is title");
    assertThat(questionContent.getContent()).isEqualTo("this is content");
  }

  @Test
  void retrieveQuestionDetail() {
    QuestionDetailResponseDto response = questionRetrieveService.retrieveQuestionDetail(
        questionedId, writerId);
    QuestionReplyResponse questionReplyResponse = response.getQuestionReplyResponse();

    assertThat(response.getId()).isEqualTo(questionedId);
    assertThat(response.getWriterId()).isEqualTo(writerId);
    assertThat(response.getTitle()).isEqualTo("this is title");
    assertThat(response.getContent()).isEqualTo("this is content");
    assertThat(response.getStatus()).isEqualTo(DONE);

    if (questionReplyResponse != null) {
      assertThat(questionReplyResponse.getReplyWriterId()).isEqualTo("ADMIN-001");
      assertThat(questionReplyResponse.getReplyContent()).isEqualTo("this is reply content");
    }
  }

  @Test
  void retrieveQuestionByAdmin() {
    AdminQuestionRetrieveResponse response = questionRetrieveService.retrieveQuestionByAdmin(
        Role.ADMIN.name(), 0, 5);
    assertThat(response.getCurrentPage()).isSameAs(1L);
    assertThat(response.getStartPage()).isSameAs(1L);
    assertThat(response.getLastPage()).isSameAs(1L);
    assertThat(response.getTotalPage()).isSameAs(1L);

    AdminQuestionRetrieveResponse.QuestionContent questionContent = response.getContents().get(0);
    assertThat(questionContent.getStatus()).isEqualTo(DONE);
    assertThat(questionContent.getTitle()).isEqualTo("this is title");
    assertThat(questionContent.getContent()).isEqualTo("this is content");
    assertThat(questionContent.getEmail()).isEqualTo("test@gmail.com");
  }
}
