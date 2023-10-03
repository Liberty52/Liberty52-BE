package com.liberty52.product.service.controller;

import com.liberty52.product.service.applicationservice.ImageUpscalingService;
import com.liberty52.product.service.controller.dto.ImageUpscalingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ImageUpscalingController {
    private final ImageUpscalingService service;

    @PostMapping("/images/upscaling")
    @ResponseStatus(HttpStatus.CREATED)
    public ImageUpscalingDto.Response generateImage(
        @Validated @RequestBody ImageUpscalingDto.Request requestBody
    ) {
        return service.upscale(requestBody.url(), requestBody.scale());
    }
}
