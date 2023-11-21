package com.liberty52.auth.service.applicationservice;

import com.liberty52.auth.global.exception.external.forbidden.InvalidRoleException;
import com.liberty52.auth.global.exception.external.notfound.QuestionNotFoundById;
import com.liberty52.auth.question.entity.Question;
import com.liberty52.auth.question.entity.QuestionReply;
import com.liberty52.auth.question.entity.QuestionStatus;
import com.liberty52.auth.question.repository.QuestionRepository;
import com.liberty52.auth.question.service.QuestionReplyCreateService;
import com.liberty52.auth.question.web.dto.QuestionReplyCreateRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class QuestionReplyCreateServiceTest {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    QuestionReplyCreateService questionReplyCreateService;

    String adminId = "adminId";
    String writerId = "testId";
    String questionId;

    @BeforeEach
    void init() {
        String title = "제목";
        String content = "내용";
        Question question = Question.create(title, content, writerId);
        questionId = question.getId();
        questionRepository.save(question);

    }

    @Test
    void 문의답변추가() {
        String role = "ADMIN";
        QuestionReplyCreateRequestDto dto = QuestionReplyCreateRequestDto.create(questionId, "답변");
        questionReplyCreateService.createQuestionReplyByAdmin(adminId, role, dto);
        Question question = questionRepository.findById(questionId).orElseGet(null);
        QuestionReply questionReply = question.getQuestionReply();

        assertThat(question.getStatus()).isEqualTo(QuestionStatus.DONE);
        assertThat(questionReply.getContent()).isEqualTo("답변");
        assertThat(questionReply.getWriterId()).isEqualTo(adminId);
        assertThat(questionReply.getCreatedAt().toLocalDate()).isEqualTo(LocalDate.now());


        QuestionReplyCreateRequestDto errDto = QuestionReplyCreateRequestDto.create("err", "답변");
        Assertions.assertThrows(InvalidRoleException.class,() -> questionReplyCreateService.createQuestionReplyByAdmin(adminId, "손님", dto));
        Assertions.assertThrows(QuestionNotFoundById.class,() -> questionReplyCreateService.createQuestionReplyByAdmin(adminId, role, errDto));

    }


}
