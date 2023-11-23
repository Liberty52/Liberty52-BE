package com.liberty52.main.service.applicationservice;

import com.liberty52.main.MockS3Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ReviewRemoveServiceTest extends MockS3Test {
    /*TODO: -- 테스트 수정 필요*/
//    @Autowired
//    ReviewRepository reviewRepository;
//
//    @Autowired
//    ReviewRemoveService reviewRemoveService;
//
//    @Autowired
//    ApplicationEventPublisher eventPublisher;
//
//    @Autowired
//    ReplyRepository replyRepository;
//    String reviewerId;
//    private Review review;
//    private Reply reply;
//
//    @BeforeEach
//    void beforeEach() {
//        review = DBInitConfig.DBInitService.getReview();
//        reply = review.getReplies().get(0);
//    }
//
//    @Test
//    void 리뷰삭제() {
//        Review reviewBefore = reviewRepository.findById(review.getId()).orElse(null);
//        Assertions.assertNotNull(reviewBefore);
//
//        Assertions.assertThrows(ReviewNotFoundByIdException.class, () -> reviewRemoveService.removeReview(reviewerId, randomString()));
//        Assertions.assertThrows(NotYourReviewException.class, () -> reviewRemoveService.removeReview(randomString(), review.getId()));
//
//        reviewRemoveService.removeReview(review.getCustomProduct().getOrders().getAuthId(), review.getId());
//        Review reviewAfter = reviewRepository.findById(review.getId()).orElse(null);
//        Assertions.assertNull(reviewAfter);
//
//        System.out.println(reviewBefore.getReplies().size());
//        for (Reply reply : reviewBefore.getReplies()) {
//            System.out.println(reply.getContent());
//        }
//    }
//
//    @Test
//    void 고객리뷰삭제() {
//        Review reviewBefore = reviewRepository.findById(review.getId()).orElse(null);
//        Assertions.assertNotNull(reviewBefore);
//
//        String role = "ADMIN";
//
//        Assertions.assertThrows(InvalidRoleException.class, () -> reviewRemoveService.removeCustomerReviewByAdmin(randomString(), review.getId()));
//        Assertions.assertThrows(ReviewNotFoundByIdException.class, () -> reviewRemoveService.removeCustomerReviewByAdmin(role, randomString()));
//
//
//        reviewRemoveService.removeCustomerReviewByAdmin(role, review.getId());
//        Review reviewAfter = reviewRepository.findById(review.getId()).orElse(null);
//        Assertions.assertNull(reviewAfter);
//    }
//
//    @Test
//    void 리뷰답변삭제() {
//        Reply replyBefore = replyRepository.findById(reply.getId()).orElse(null);
//        Assertions.assertNotNull(replyBefore);
//
//        reviewRemoveService.removeReplyByAdmin("adminId", "ADMIN", reply.getId());
//        Review reviewAfter = reviewRepository.findById(review.getId()).orElse(null);
//        Reply replyAfter = replyRepository.findById(reply.getId()).orElse(null);
//        Assertions.assertNull(replyAfter);
//        Assertions.assertEquals(reviewAfter.getReplies().size(), 2);
//
//    }
//
//
//    private String randomString() {
//        return UUID.randomUUID().toString();
//    }
}
