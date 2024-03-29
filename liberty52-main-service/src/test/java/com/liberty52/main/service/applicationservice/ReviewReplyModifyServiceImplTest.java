package com.liberty52.main.service.applicationservice;

import com.liberty52.common.exception.external.badrequest.BadRequestException;
import com.liberty52.common.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.main.global.data.DBInitConfig.DBInitService;
import com.liberty52.main.global.exception.external.forbidden.InvalidRoleException;
import com.liberty52.main.service.controller.dto.ReplyModifyRequestDto;
import com.liberty52.main.service.entity.*;
import com.liberty52.main.service.repository.ReplyRepository;
import com.liberty52.main.service.repository.ReviewRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.liberty52.main.global.constants.RoleConstants.ADMIN;

@Transactional
@SpringBootTest
class ReviewReplyModifyServiceImplTest {

    final String mockAdminId = "bar";
    final String mockReplyContent = "Hello world";
    @Autowired
    ReviewReplyModifyService service;
    String reviewId;
    String replyId;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ReplyRepository replyRepository;

    @BeforeEach
    void beforeEach() {
        Review review = DBInitService.getReview();
        Reply reply = Reply.create(mockReplyContent, mockAdminId);
        reply.associate(review);
        reviewRepository.save(review);
        reviewId = review.getId();
        replyId = reply.getId();
    }

    @Test
    void Modify_Reply_Success() {
        String newContent = UUID.randomUUID().toString();
        service.modifyReviewReplyByAdmin(mockAdminId, ADMIN, ReplyModifyRequestDto.createForTest(newContent), reviewId, replyId);
        Reply reply = replyRepository.findById(replyId).get();
        Assertions.assertEquals(newContent, reply.getContent());
    }

    @Test
    void InvalidRoleException() {
        Assertions.assertThrows(InvalidRoleException.class, () -> service.modifyReviewReplyByAdmin(mockAdminId, UUID.randomUUID().toString(), ReplyModifyRequestDto.createForTest(UUID.randomUUID().toString()), reviewId, replyId));
    }

    @Test
    void ResourceNotFoundException() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.modifyReviewReplyByAdmin(mockAdminId, ADMIN, ReplyModifyRequestDto.createForTest(UUID.randomUUID().toString()), reviewId, UUID.randomUUID().toString()));
    }

    @Test
    void BadRequest() {
        Product product = DBInitService.getProduct();
        Orders order = DBInitService.getOrder();
        CustomProduct customProduct = DBInitService.getCustomProduct();
        Review otherReview = Review.create(3, "content");

        otherReview.associate(customProduct);
        reviewRepository.save(otherReview);

        Assertions.assertThrows(BadRequestException.class, () -> service.modifyReviewReplyByAdmin(mockAdminId, ADMIN, ReplyModifyRequestDto.createForTest(UUID.randomUUID().toString()), otherReview.getId(), replyId));
    }
}
