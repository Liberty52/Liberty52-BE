package com.liberty52.main.service.controller;

import com.liberty52.main.service.applicationservice.ReviewRemoveService;
import com.liberty52.main.service.applicationservice.ReviewRetrieveService;
import com.liberty52.main.service.controller.dto.AdminReviewDetailResponse;
import com.liberty52.main.service.controller.dto.AdminReviewRetrieveResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "관리자 리뷰", description = "관리자 리뷰 관련 API를 제공합니다")
@RestController
@RequiredArgsConstructor
public class ReviewAdminController {

    private final ReviewRetrieveService reviewRetrieveService;
    private final ReviewRemoveService reviewItemRemoveService;

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

    @Operation(summary = "관리자 고객 리뷰 삭제", description = "관리자가 고객 리뷰를 삭제합니다.")
    @DeleteMapping("/admin/customerReviews/{reviewId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeCustomerReviewByAdmin(@RequestHeader(HttpHeaders.AUTHORIZATION) String adminId,
                                            @RequestHeader("LB-Role") String role, @PathVariable String reviewId) {
        reviewItemRemoveService.removeCustomerReviewByAdmin(role, reviewId);
    }

    @Operation(summary = "관리자 리뷰 답글 삭제", description = "관리자가 리뷰 답글을 삭제합니다.")
    @DeleteMapping("/admin/reviews/replies/{replyId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeReplyByAdmin(@RequestHeader(HttpHeaders.AUTHORIZATION) String adminId,
                                   @RequestHeader("LB-Role") String role, @PathVariable String replyId) {
        reviewItemRemoveService.removeReplyByAdmin(adminId, role, replyId);
    }
}
