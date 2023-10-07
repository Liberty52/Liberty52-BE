package com.liberty52.product.service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.liberty52.product.service.applicationservice.ReviewModifyService;
import com.liberty52.product.service.controller.dto.ReviewImagesRemoveRequestDto;
import com.liberty52.product.service.controller.dto.ReviewModifyRequestDto;
import java.util.List;

@Tag(name = "상품", description = "상품 관련 API를 제공합니다")
@RestController
@RequiredArgsConstructor
public class ReviewModifyController {
    private final ReviewModifyService reviewModifyService;

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
}
