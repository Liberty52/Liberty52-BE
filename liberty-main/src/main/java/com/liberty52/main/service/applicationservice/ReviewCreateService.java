package com.liberty52.main.service.applicationservice;

import com.liberty52.main.service.controller.dto.ReviewCreateRequestDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReviewCreateService {
    void createReview(String reviewerId, ReviewCreateRequestDto dto, List<MultipartFile> imageFile);
}
