package com.liberty52.auth.service.applicationservice;

import com.liberty52.auth.global.exception.external.forbidden.NotYourQuestionException;
import com.liberty52.auth.global.exception.external.notfound.QuestionNotFoundById;
import com.liberty52.auth.service.entity.Question;
import com.liberty52.auth.service.repository.QuestionRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class QuestionDeleteServiceTest {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    QuestionDeleteService questionDeleteService;

    String writerId = "testId";
    String questionId;

    @Autowired
    EntityManager em;

    @BeforeEach
    void init() {
        String title = "제목";
        String content = "내용";
        Question question = Question.create(title, content, writerId);
        questionId = question.getId();
        questionRepository.save(question);
    }

    @AfterEach
    void afterEach(){
        questionRepository.deleteAll();
    }

    @Test
    void 문의추가() {

        Question beforeQuestion = questionRepository.findById(questionId).orElseGet(null);
        assertThat(beforeQuestion.getWriterId().equals(writerId));
        Assertions.assertThrows(QuestionNotFoundById.class, () -> questionDeleteService.deleteQuestion(writerId, "err"));
        Assertions.assertThrows(NotYourQuestionException.class, () -> questionDeleteService.deleteQuestion("err", questionId));

        questionDeleteService.deleteQuestion(writerId, questionId);
        Question afterQuestion = questionRepository.findById(questionId).orElse(null);
        Assertions.assertNull(afterQuestion);
    }

    @Test
    void deleteAllWriterId_empty(){

        //given
        for (int i = 0; i < 10; i++) {
            String title = "제목";
            String content = "내용";
            Question question = Question.create(title, content, writerId);
            questionId = question.getId();
            questionRepository.save(question);
        }
        questionDeleteService.deleteAllQuestion(writerId);
        em.flush();
        em.clear();
        //when
        List<Question> result = em.createQuery("select q from Question q where q.writerId =:id",
                        Question.class)
                .setParameter("id", writerId)
                .getResultList();

        //then
        org.assertj.core.api.Assertions.assertThat(result).isEmpty();

    }
}
