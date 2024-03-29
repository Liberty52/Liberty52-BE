package com.liberty52.main.service.applicationservice;

import com.liberty52.main.service.controller.dto.AdminReviewDetailResponse;
import com.liberty52.main.service.controller.dto.AdminReviewRetrieveResponse;
import com.liberty52.main.service.controller.dto.ReviewRetrieveResponse;
import org.springframework.data.domain.Pageable;

public interface ReviewRetrieveService {

    ReviewRetrieveResponse retrieveReviews(String productId, String authorId, Pageable pageable, boolean isPhotoFilter);

    AdminReviewRetrieveResponse retrieveReviewByAdmin(String role, Pageable pageable);

    AdminReviewDetailResponse retrieveReviewDetailByAdmin(String role, String reviewId);
}
