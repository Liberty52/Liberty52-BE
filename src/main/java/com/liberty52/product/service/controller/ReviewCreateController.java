package com.liberty52.product.service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.liberty52.product.service.applicationservice.ReviewCreateService;
import com.liberty52.product.service.controller.dto.ReviewCreateRequestDto;
import java.util.List;

@Tag(name = "상품", description = "상품 관련 API를 제공합니다")
@RestController
@RequiredArgsConstructor
public class ReviewCreateController {

  private final ReviewCreateService reviewCreateService;

  @Operation(summary = "리뷰 생성", description = "사용자가 리뷰를 생성합니다.")
  @PostMapping("/reviews")
  @ResponseStatus(HttpStatus.CREATED)
  public void createReview( @RequestHeader(HttpHeaders.AUTHORIZATION) String reviewerId, @Validated @RequestPart ReviewCreateRequestDto dto,
                            @RequestPart(value = "images",required = false) List<MultipartFile> images) {
    reviewCreateService.createReview(reviewerId,dto,images);
  }
}
