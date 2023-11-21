package com.liberty52.main.service.applicationservice;

import com.liberty52.main.service.controller.dto.ImageUpscalingDto;

public interface ImageUpscalingService {
    ImageUpscalingDto.Response upscale(String url, Integer scale);
}
