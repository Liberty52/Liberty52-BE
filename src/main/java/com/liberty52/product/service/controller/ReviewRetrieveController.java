package com.liberty52.product.service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.liberty52.product.service.applicationservice.ReviewRetrieveService;
import com.liberty52.product.service.controller.dto.AdminReviewDetailResponse;
import com.liberty52.product.service.controller.dto.AdminReviewRetrieveResponse;
import com.liberty52.product.service.controller.dto.ReviewRetrieveResponse;

@Tag(name = "리뷰", description = "리뷰 관련 API를 제공합니다")
@Slf4j
@RequiredArgsConstructor
@RestController
public class ReviewRetrieveController {

    private final ReviewRetrieveService reviewRetrieveService;

    @Operation(summary = "상품 리뷰 조회", description = "특정 상품에 대한 리뷰를 조회합니다.")
    @GetMapping("/reviews/products/{productId}")
    public ResponseEntity<ReviewRetrieveResponse> retrieveReview(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String identifier,
            @PathVariable String productId,
            @Parameter(description = "페이지 정보", hidden = true) Pageable pageable,
            @RequestParam(required = false) boolean photoFilter
    ) {
        ReviewRetrieveResponse response = reviewRetrieveService.retrieveReviews(
                productId, identifier, pageable, photoFilter);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "관리자 리뷰 조회", description = "관리자 권한으로 모든 리뷰를 조회합니다.")
    @GetMapping("/admin/reviews")
    public ResponseEntity<AdminReviewRetrieveResponse> retrieveReviewByAdmin(@RequestHeader("LB-Role") String role,
                                                                             @Parameter(description = "페이지 정보", hidden = true) Pageable pageable) {
        AdminReviewRetrieveResponse response = reviewRetrieveService.retrieveReviewByAdmin(role, pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "관리자 리뷰 상세 조회", description = "관리자 권한으로 특정 리뷰의 상세 정보를 조회합니다.")
    @GetMapping("/admin/reviews/{reviewId}")
    public ResponseEntity<AdminReviewDetailResponse> retrieveReviewDetailByAdmin(@RequestHeader("LB-Role") String role,
                                                                                 @PathVariable String reviewId) {
        AdminReviewDetailResponse response = reviewRetrieveService.retrieveReviewDetailByAdmin(role, reviewId);
        return ResponseEntity.ok(response);
    }
}
