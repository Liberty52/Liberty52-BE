package com.liberty52.product.service.controller;

import com.liberty52.product.service.applicationservice.ImageUpscalingService;
import com.liberty52.product.service.controller.dto.ImageUpscalingDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "이미지", description = "이미지 관련 API를 제공합니다")
public class ImageUpscalingController {
    private final ImageUpscalingService service;

    @PostMapping("/images/upscaling")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "이미지 업스케일링", description = "이미지를 업스케일링합니다.")
    public ImageUpscalingDto.Response generateImage(
        @Validated @RequestBody ImageUpscalingDto.Request requestBody
    ) {
        return service.upscale(requestBody.url(), requestBody.scale());
    }
}
