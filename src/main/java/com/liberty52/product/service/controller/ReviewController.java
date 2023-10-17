package com.liberty52.product.service.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.liberty52.product.service.applicationservice.ReviewCreateService;
import com.liberty52.product.service.applicationservice.ReviewModifyService;
import com.liberty52.product.service.applicationservice.ReviewRemoveService;
import com.liberty52.product.service.applicationservice.ReviewRetrieveService;
import com.liberty52.product.service.controller.dto.AdminReviewDetailResponse;
import com.liberty52.product.service.controller.dto.AdminReviewRetrieveResponse;
import com.liberty52.product.service.controller.dto.ReviewCreateRequestDto;
import com.liberty52.product.service.controller.dto.ReviewImagesRemoveRequestDto;
import com.liberty52.product.service.controller.dto.ReviewModifyRequestDto;
import com.liberty52.product.service.controller.dto.ReviewRetrieveResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "리뷰", description = "리뷰 관련 API를 제공합니다")
@RestController
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewCreateService reviewCreateService;

	private final ReviewRetrieveService reviewRetrieveService;
	private final ReviewModifyService reviewModifyService;
	private final ReviewRemoveService reviewItemRemoveService;

	@Operation(summary = "리뷰 생성", description = "사용자가 리뷰를 생성합니다.")
	@PostMapping("/reviews")
	@ResponseStatus(HttpStatus.CREATED)
	public void createReview(@RequestHeader(HttpHeaders.AUTHORIZATION) String reviewerId,
		@Validated @RequestPart ReviewCreateRequestDto dto,
		@RequestPart(value = "images", required = false) List<MultipartFile> images) {
		reviewCreateService.createReview(reviewerId, dto, images);
	}

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

	@Operation(summary = "리뷰 수정", description = "사용자가 리뷰를 수정합니다.")
	@PutMapping("/reviews/{reviewId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void modifyReview(@RequestHeader(HttpHeaders.AUTHORIZATION) String reviewerId,
		@PathVariable String reviewId,
		@Validated @RequestPart ReviewModifyRequestDto dto,
		@RequestPart(required = false) List<MultipartFile> images) {
		reviewModifyService.modifyReview(reviewerId, reviewId, dto, images);
	}

	@Operation(summary = "리뷰 등급 및 내용 수정", description = "사용자가 리뷰의 등급 및 내용을 수정합니다.")
	@PatchMapping("/reviews/{reviewId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void modifyReviewRatingContent(@RequestHeader(HttpHeaders.AUTHORIZATION) String reviewerId,
		@PathVariable String reviewId,
		@Validated @RequestBody ReviewModifyRequestDto dto) {
		reviewModifyService.modifyReviewRatingContent(reviewerId, reviewId, dto);
	}

	@Operation(summary = "리뷰 이미지 추가", description = "사용자가 리뷰에 이미지를 추가합니다.")
	@PostMapping("/reviews/{reviewId}/images") // 이미지 추가
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void addReviewImages(@RequestHeader(HttpHeaders.AUTHORIZATION) String reviewerId,
		@PathVariable String reviewId,
		@RequestPart List<MultipartFile> images) {
		reviewModifyService.addReviewImages(reviewerId, reviewId, images);
	}

	@Operation(summary = "리뷰 이미지 삭제", description = "사용자가 리뷰에서 이미지를 삭제합니다.")
	@DeleteMapping("/reviews/{reviewId}/images")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeReviewImages(@RequestHeader(HttpHeaders.AUTHORIZATION) String reviewerId,
		@PathVariable String reviewId,
		@Validated @RequestBody ReviewImagesRemoveRequestDto dto) {
		reviewModifyService.removeReviewImages(reviewerId, reviewId, dto);
	}

	@Operation(summary = "리뷰 삭제", description = "사용자가 리뷰를 삭제합니다.")
	@DeleteMapping("/reviews/{reviewId}")
	@ResponseStatus(HttpStatus.OK)
	public void removeReview(@RequestHeader(HttpHeaders.AUTHORIZATION) String reviewerId,
		@PathVariable String reviewId) {
		reviewItemRemoveService.removeReview(reviewerId, reviewId);
	}

	@Operation(summary = "관리자에 의한 고객 리뷰 삭제", description = "관리자가 고객 리뷰를 삭제합니다.")
	@DeleteMapping("/admin/customerReviews/{reviewId}")
	@ResponseStatus(HttpStatus.OK)
	public void removeCustomerReviewByAdmin(@RequestHeader(HttpHeaders.AUTHORIZATION) String adminId,
		@RequestHeader("LB-Role") String role, @PathVariable String reviewId) {
		reviewItemRemoveService.removeCustomerReviewByAdmin(role, reviewId);
	}

	@Operation(summary = "관리자에 의한 리뷰 답글 삭제", description = "관리자가 리뷰 답글을 삭제합니다.")
	@DeleteMapping("/admin/reviews/replies/{replyId}")
	@ResponseStatus(HttpStatus.OK)
	public void removeReplyByAdmin(@RequestHeader(HttpHeaders.AUTHORIZATION) String adminId,
		@RequestHeader("LB-Role") String role, @PathVariable String replyId) {
		reviewItemRemoveService.removeReplyByAdmin(adminId, role, replyId);
	}
}
