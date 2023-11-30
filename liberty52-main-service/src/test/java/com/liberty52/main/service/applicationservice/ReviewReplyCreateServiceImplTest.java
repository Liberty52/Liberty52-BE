package com.liberty52.main.service.applicationservice;

import com.liberty52.common.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.main.global.data.DBInitConfig.DBInitService;
import com.liberty52.main.global.exception.external.forbidden.InvalidRoleException;
import com.liberty52.main.service.controller.dto.ReplyCreateRequestDto;
import com.liberty52.main.service.entity.Reply;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static com.liberty52.main.global.constants.RoleConstants.ADMIN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class ReviewReplyCreateServiceImplTest {

    final String mockAdminId = "bar";
    final String mockUserRole = "USER";
    final String mockReplyContent = "Hello world";
    final String mockReviewId = "foo";
    @Autowired
    ReviewReplyCreateService service;
    @Autowired
    EntityManager em;

    @AfterEach
    void afterEach() {

    }

    @Test
    void Create_Reply_Success() throws Exception {
        //given
        String reviewId = DBInitService.getReview().getId();
        ReplyCreateRequestDto dto = ReplyCreateRequestDto.createForTest(mockReplyContent);
        service.createReviewReplyByAdmin(mockAdminId, dto, reviewId, ADMIN);
        //when
        Reply finded = em.createQuery(
                        "select r from Reply r where r.review.id = :id and r.authId = :authId", Reply.class)
                .setParameter("id", reviewId)
                .setParameter("authId", mockAdminId)
                .getSingleResult();

        //then
        assertThat(finded).isNotNull();
        assertThat(finded.getContent()).isEqualTo(mockReplyContent);
        assertThat(finded.getAuthId()).isEqualTo(mockAdminId);


    }

    @Test
    void CREATE_REPLY_FAIL_InvalidRoleException() throws Exception {
        //given
        String reviewId = mockReviewId;
        ReplyCreateRequestDto dto = ReplyCreateRequestDto.createForTest(mockReplyContent);
        //when
        assertThatThrownBy(() -> service.createReviewReplyByAdmin(mockAdminId, dto, reviewId, mockUserRole))
                .isInstanceOf(InvalidRoleException.class);

    }

    @Test
    void CREATE_REPLY_FAIL_ResourceNotFoundException() throws Exception {
        //given
        String reviewId = mockReviewId;
        ReplyCreateRequestDto dto = ReplyCreateRequestDto.createForTest(mockReplyContent);
        //when
        assertThatThrownBy(() -> service.createReviewReplyByAdmin(mockAdminId, dto, reviewId, ADMIN))
                .isInstanceOf(ResourceNotFoundException.class);

        //then

    }

}
