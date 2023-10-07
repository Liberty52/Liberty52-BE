package com.liberty52.product.service.controller;

import com.liberty52.product.service.applicationservice.ReviewRemoveService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "상품", description = "상품 관련 API를 제공합니다")
@RestController
@RequiredArgsConstructor
public class ReviewRemoveController {
    private final ReviewRemoveService reviewItemRemoveService;

    @DeleteMapping("/reviews/{reviewId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeReview(@RequestHeader(HttpHeaders.AUTHORIZATION) String reviewerId, @PathVariable String reviewId) {
        reviewItemRemoveService.removeReview(reviewerId, reviewId);
    }

    @DeleteMapping("/admin/customerReviews/{reviewId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeCustomerReviewByAdmin(@RequestHeader(HttpHeaders.AUTHORIZATION) String adminId, @RequestHeader("LB-Role") String role, @PathVariable String reviewId) {

        reviewItemRemoveService.removeCustomerReviewByAdmin(role, reviewId);
    }

    @DeleteMapping("/admin/reviews/replies/{replyId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeReplyByAdmin(@RequestHeader(HttpHeaders.AUTHORIZATION) String adminId, @RequestHeader("LB-Role") String role, @PathVariable String replyId) {
        reviewItemRemoveService.removeReplyByAdmin(adminId, role ,replyId);
    }
}
