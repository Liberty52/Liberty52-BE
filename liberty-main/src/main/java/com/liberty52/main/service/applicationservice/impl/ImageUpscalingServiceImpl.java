package com.liberty52.main.service.applicationservice.impl;

import com.liberty52.main.global.adapter.ImageUpscaler;
import com.liberty52.main.global.exception.external.internalservererror.InternalServerErrorException;
import com.liberty52.main.service.applicationservice.ImageUpscalingService;
import com.liberty52.main.service.controller.dto.ImageUpscalingDto;
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
                ).doOnError(this::throwError)
                .blockOptional()
                .orElseThrow(this::emptyResponseError);
        return new ImageUpscalingDto.Response(url, afterUrl);
    }

    private void throwError(Throwable error) {
        log.error("{}::throwError {}", this.getClass().getSimpleName(), error.getMessage());
        throw new GeneratorInternalError();
    }

    private InternalServerErrorException emptyResponseError() {
        var error = new GeneratorInternalError();
        log.error("{}::throwError {}", this.getClass().getSimpleName(), error.getMessage());
        return error;
    }

    private static class GeneratorInternalError extends InternalServerErrorException {
        public GeneratorInternalError() {
            super("다음의 가능성이 있습니다. 잘못된 url, scale. API rate limit 초과. API key 만료.");
        }
    }
}
