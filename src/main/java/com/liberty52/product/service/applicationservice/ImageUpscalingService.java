package com.liberty52.product.service.applicationservice;

import com.liberty52.product.service.controller.dto.ImageUpscalingDto;

public interface ImageUpscalingService {
    ImageUpscalingDto.Response upscale(String url, Integer scale);
}
