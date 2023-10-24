package com.liberty52.product.service.applicationservice.impl;

import com.liberty52.product.global.adapter.dalle.DallEApiClient;
import com.liberty52.product.global.exception.external.internalservererror.InternalServerErrorException;
import com.liberty52.product.service.applicationservice.ImageGenerationService;
import com.liberty52.product.service.controller.dto.ImageGenerationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageGenerationServiceImpl implements ImageGenerationService {
    private final DallEApiClient imageGenerator;

    @Override
    public ImageGenerationDto.Response generateImage(String authId, ImageGenerationDto.Request dto) {
        List<String> urls = imageGenerator
            .generate(dto.prompt(), new Dimension(1024, 1024), dto.n())
            .doOnError(this::throwError)
            .blockOptional()
            .orElseThrow(this::emptyResponseError);
        return new ImageGenerationDto.Response(urls);
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
            super("다음의 가능성이 있습니다. prompt의 길이가 너무 큼. n이 너무 큼. API rate limit 초과. API key 만료.");
        }
    }
}
