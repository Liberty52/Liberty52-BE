package com.liberty52.main.service.applicationservice;

import com.liberty52.main.service.controller.dto.ReplyCreateRequestDto;

public interface ReviewReplyCreateService {
    void createReviewReplyByAdmin(String adminId, ReplyCreateRequestDto dto, String reviewId, String role);

}
