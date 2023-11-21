package com.liberty52.auth.service.applicationservice;

import com.liberty52.auth.question.entity.Question;
import com.liberty52.auth.question.entity.QuestionReply;
import com.liberty52.auth.question.repository.QuestionReplyRepository;
import com.liberty52.auth.question.repository.QuestionRepository;
import com.liberty52.auth.question.service.QuestionReplyModifyService;
import com.liberty52.auth.question.web.dto.QuestionReplyModifyRequestDto;
import com.liberty52.auth.user.entity.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional
@SpringBootTest
public class QuestionReplyModifyServiceTest {
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    QuestionReplyModifyService questionReplyModifyService;
    String adminId = "adminId";
    String writerId = "testId";
    String questionId;
    String qrId;
    @Autowired
    private QuestionReplyRepository questionReplyRepository;

    @BeforeEach
    void init() {
        String title = "제목";
        String content = "내용";
        Question question = Question.create(title, content, writerId);
        questionId = question.getId();
        questionRepository.save(question);


        QuestionReply qr = QuestionReply.create("some content", adminId);
        qrId = qr.getId();
        qr.associate(question);

        questionRepository.save(question);
    }

    @Test
    void basicPath() {
        String modifiedContent = UUID.randomUUID().toString();
        questionReplyModifyService.modifyQuestionReplyByAdmin(adminId, Role.ADMIN.name(), qrId, QuestionReplyModifyRequestDto.createForTest(modifiedContent));

        QuestionReply qr = questionReplyRepository.findById(qrId).get();
        Assertions.assertEquals(modifiedContent, qr.getContent());
    }


}
