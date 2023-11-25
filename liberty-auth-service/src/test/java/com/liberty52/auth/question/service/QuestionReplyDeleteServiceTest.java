package com.liberty52.auth.question.service;

import com.liberty52.auth.question.entity.Question;
import com.liberty52.auth.question.entity.QuestionReply;
import com.liberty52.auth.question.entity.QuestionStatus;
import com.liberty52.auth.question.repository.QuestionReplyRepository;
import com.liberty52.auth.question.repository.QuestionRepository;
import com.liberty52.auth.question.web.dto.QuestionReplyCreateRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class QuestionReplyDeleteServiceTest {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    QuestionReplyDeleteService questionReplyDeleteService;

    @Autowired
    QuestionReplyRepository questionReplyRepository;

    String adminId = "adminId";
    String writerId = "testId";
    String questionId;
    String role = "ADMIN";
    String questionReplyId;

    @BeforeEach
    void init() {
        String title = "제목";
        String content = "내용";
        Question question = Question.create(title, content, writerId);
        questionId = question.getId();

        QuestionReplyCreateRequestDto dto = QuestionReplyCreateRequestDto.create(questionId, "답변");
        QuestionReply questionReply = QuestionReply.create(dto.getContent(), adminId);
        questionReplyId = questionReply.getId();
        questionReply.associate(question);
        questionRepository.save(question);
    }

    @Test
    void 문의답변삭제() {

        Question beforeQuestion = questionRepository.findById(questionId).orElseGet(null);
        assertThat(beforeQuestion.getQuestionReply().getContent().equals("답변"));

        questionReplyDeleteService.deleteQuestionReplyByAdmin(adminId, role, questionReplyId);

        QuestionReply questionReply =questionReplyRepository.findById(questionReplyId).orElse(null);
        Question afterQuestion = questionRepository.findById(questionId).orElse(null);
        assertThat(afterQuestion.getStatus().equals(QuestionStatus.DONE));
        Assertions.assertNull(afterQuestion.getQuestionReply());
        Assertions.assertNull(questionReply);
    }

}
