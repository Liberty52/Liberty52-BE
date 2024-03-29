package com.liberty52.main.service.applicationservice;

import com.liberty52.main.TestBeanConfig;
import com.liberty52.main.global.adapter.cloud.AuthServiceClient;
import com.liberty52.main.global.data.DBInitConfig.DBInitService;
import com.liberty52.main.service.controller.dto.AdminReviewDetailResponse;
import com.liberty52.main.service.controller.dto.AdminReviewRetrieveResponse;
import com.liberty52.main.service.controller.dto.ReviewRetrieveResponse;
import com.liberty52.main.service.controller.dto.ReviewRetrieveResponse.ReplyContent;
import com.liberty52.main.service.controller.dto.ReviewRetrieveResponse.ReviewContent;
import com.liberty52.main.service.entity.Orders;
import com.liberty52.main.service.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;

import static com.liberty52.main.global.constants.RoleConstants.ADMIN;
import static com.liberty52.main.service.utils.MockConstants.MOCK_AUTHOR_NAME;
import static com.liberty52.main.service.utils.MockConstants.MOCK_AUTHOR_PROFILE_URL;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Import({TestBeanConfig.class})
class ReviewRetrieveServiceImplTest {

    private final String reviewId = "REVIEW-001";
    @Autowired
    ReviewRetrieveService reviewRetrieveService;
    @Autowired
    AuthServiceClient authServiceClient;
    Orders order;
    Product product;

    @BeforeEach
    void beforeEach() {
        order = DBInitService.getOrder();
        product = DBInitService.getProduct();
    }


    @Test
    void retrieveReviews() throws Exception {
        //given
        //when
        ReviewRetrieveResponse response = reviewRetrieveService.retrieveReviews(
                product.getId(), order.getAuthId(), PageRequest.of(0, 5), false);

        //then
        assertThat(response.getCurrentPage()).isSameAs(1L);
        assertThat(response.getStartPage()).isSameAs(1L);
//        assertThat(response.getLastPage()).isSameAs(1L);
//        assertThat(response.getContents().size()).isSameAs(2);
        // 추가
        assertThat(response.getLastPage()).isSameAs(3L);
        assertThat(response.getContents().size()).isSameAs(5);

        ReviewContent content = response.getContents().get(0);
        assertThat(content.getContent()).isEqualTo("good");
        assertThat(content.getAuthorName()).isEqualTo(MOCK_AUTHOR_NAME);
        assertThat(content.getAuthorProfileUrl()).isEqualTo(MOCK_AUTHOR_PROFILE_URL);
        assertThat(content.getRating()).isSameAs(3);
//        assertThat(content.getImageUrls().size()).isSameAs(1);

        for (int i = 0; i < content.getNOfReply(); i++) {
            ReplyContent replyContent = content.getReplies().get(i);
            assertThat(replyContent.getAuthorId()).isEqualTo(order.getAuthId());
            assertThat(replyContent.getAuthorName()).isEqualTo(MOCK_AUTHOR_NAME);
            assertThat(replyContent.getAuthorProfileUrl()).isEqualTo(MOCK_AUTHOR_PROFILE_URL);
            assertThat(replyContent.getContent()).isEqualTo("맛있따" + i);
        }
    }

    @Test
    void retrieveReviewByAdmin() {
        //given
        //when
        AdminReviewRetrieveResponse response =
                reviewRetrieveService.retrieveReviewByAdmin(ADMIN, PageRequest.of(0, 5));

        //then
        assertThat(response.getCurrentPage()).isSameAs(1L);
        assertThat(response.getStartPage()).isSameAs(1L);
        assertThat(response.getLastPage()).isSameAs(3L);
        assertThat(response.getContents().size()).isSameAs(5);

        AdminReviewRetrieveResponse.ReviewContent content = response.getContents().get(0);
        assertThat(content.getContent()).isEqualTo("good");
        assertThat(content.getAuthorName()).isEqualTo(MOCK_AUTHOR_NAME);
        assertThat(content.getRating()).isSameAs(3);
    }

    @Test
    void retrieveReviewDetailByAdmin() {
        //given
        //when
        AdminReviewDetailResponse response = reviewRetrieveService.retrieveReviewDetailByAdmin(ADMIN, reviewId);
        AdminReviewDetailResponse.ReviewContent detailResponse = response.getContent();
        //then
        assertThat(detailResponse.getReviewId()).isSameAs(reviewId);
        assertThat(detailResponse.getRating()).isSameAs(3);
        assertThat(detailResponse.getContent()).isSameAs("good");
        assertThat(detailResponse.getAuthorId()).isSameAs(order.getAuthId());
        assertThat(detailResponse.getAuthorName()).isSameAs(MOCK_AUTHOR_NAME);

        for (int i = 0; i < detailResponse.getReplies().size(); i++) {
            AdminReviewDetailResponse.ReplyContent replyContent = detailResponse.getReplies().get(i);
            assertThat(replyContent.getAuthorId()).isEqualTo(order.getAuthId());
            assertThat(replyContent.getAuthorName()).isEqualTo(MOCK_AUTHOR_NAME);
            assertThat(replyContent.getContent()).isEqualTo("맛있따" + i);
        }
    }
}
