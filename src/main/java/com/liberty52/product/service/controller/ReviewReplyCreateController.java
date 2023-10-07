package com.liberty52.product.service.controller;


import com.liberty52.product.service.applicationservice.ReviewReplyCreateService;
import com.liberty52.product.service.controller.dto.ReplyCreateRequestDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "상품", description = "상품 관련 API를 제공합니다")
@RestController
@RequiredArgsConstructor
public class ReviewReplyCreateController {

    private final ReviewReplyCreateService reviewReplyCreateService;

    @PostMapping("/admin/reviews/{reviewId}/replies")
    @ResponseStatus(HttpStatus.CREATED)
    public void createReviewReplyByAdmin(@RequestHeader(HttpHeaders.AUTHORIZATION) String adminId,
            @RequestHeader("LB-Role") String role,
            @Validated @RequestBody ReplyCreateRequestDto dto, @PathVariable String reviewId) {
        reviewReplyCreateService.createReviewReplyByAdmin(adminId,dto,reviewId,role);
    }

}
