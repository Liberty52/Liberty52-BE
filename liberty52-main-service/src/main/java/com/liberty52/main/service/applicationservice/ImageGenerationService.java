package com.liberty52.main.service.applicationservice;

import com.liberty52.main.service.controller.dto.ImageGenerationDto;

public interface ImageGenerationService {
    ImageGenerationDto.Response generateImage(String authId, ImageGenerationDto.Request dto);
}
