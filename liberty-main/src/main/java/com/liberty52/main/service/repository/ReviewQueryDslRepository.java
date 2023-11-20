package com.liberty52.main.service.repository;

import com.liberty52.main.service.controller.dto.AdminReviewDetailResponse;
import com.liberty52.main.service.controller.dto.AdminReviewRetrieveResponse;
import com.liberty52.main.service.controller.dto.ReviewRetrieveResponse;
import com.liberty52.main.service.entity.Review;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewQueryDslRepository {

    ReviewRetrieveResponse retrieveReview(String productId, String authorId, Pageable pageable,
                                          boolean isPhotoFilter);

    List<Review> retrieveReviewByWriterId(String writerId);

    AdminReviewRetrieveResponse retrieveAllReviews(Pageable pageable);

    AdminReviewDetailResponse retrieveReviewDetail(String reviewId);
}
