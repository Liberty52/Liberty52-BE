package com.liberty52.product.service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.liberty52.product.service.applicationservice.ReviewRemoveService;

@Tag(name = "상품", description = "상품 관련 API를 제공합니다")
@RestController
@RequiredArgsConstructor
public class ReviewRemoveController {
    private final ReviewRemoveService reviewItemRemoveService;

    @Operation(summary = "리뷰 삭제", description = "사용자가 리뷰를 삭제합니다.")
    @DeleteMapping("/reviews/{reviewId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeReview(@RequestHeader(HttpHeaders.AUTHORIZATION) String reviewerId, @PathVariable String reviewId) {
        reviewItemRemoveService.removeReview(reviewerId, reviewId);
    }

    @Operation(summary = "관리자에 의한 고객 리뷰 삭제", description = "관리자가 고객 리뷰를 삭제합니다.")
    @DeleteMapping("/admin/customerReviews/{reviewId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeCustomerReviewByAdmin(@RequestHeader(HttpHeaders.AUTHORIZATION) String adminId, @RequestHeader("LB-Role") String role, @PathVariable String reviewId) {
        reviewItemRemoveService.removeCustomerReviewByAdmin(role, reviewId);
    }

    @Operation(summary = "관리자에 의한 리뷰 답글 삭제", description = "관리자가 리뷰 답글을 삭제합니다.")
    @DeleteMapping("/admin/reviews/replies/{replyId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeReplyByAdmin(@RequestHeader(HttpHeaders.AUTHORIZATION) String adminId, @RequestHeader("LB-Role") String role, @PathVariable String replyId) {
        reviewItemRemoveService.removeReplyByAdmin(adminId, role, replyId);
    }
}
