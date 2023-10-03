package com.liberty52.product.service.applicationservice.impl;

import com.liberty52.product.global.adapter.ImageUpscaler;
import com.liberty52.product.global.exception.external.internalservererror.InternalServerErrorException;
import com.liberty52.product.service.applicationservice.ImageUpscalingService;
import com.liberty52.product.service.controller.dto.ImageUpscalingDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageUpscalingServiceImpl implements ImageUpscalingService {
    private final ImageUpscaler imageUpscaler;

    @Override
    public ImageUpscalingDto.Response upscale(String url, Integer scale) {
        String afterUrl = imageUpscaler.upscale(
            url,
            new ImageUpscaler.Scale.Mul(scale.doubleValue())
        ).getOrElseThrow(() -> new InternalServerErrorException("다음의 가능성이 있습니다. 잘못된 url, scale. API rate limit 초과. API key 만료."));
        return new ImageUpscalingDto.Response(url, afterUrl);
    }
}
