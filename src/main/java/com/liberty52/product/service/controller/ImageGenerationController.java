package com.liberty52.product.service.controller;

import com.liberty52.product.service.applicationservice.ImageGenerationService;
import com.liberty52.product.service.controller.dto.ImageGenerationDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "이미지", description = "이미지 관련 API를 제공합니다")
@RestController
@RequiredArgsConstructor
public class ImageGenerationController {
    private final ImageGenerationService service;

    @Operation(summary = "이미지 생성", description = "이미지를 생성합니다.")
    @PostMapping("/images/generations")
    @ResponseStatus(HttpStatus.CREATED)
    public ImageGenerationDto.Response generateImage(@RequestHeader(HttpHeaders.AUTHORIZATION) String authId, // 어뷰징 방지.
                                            @Validated @RequestBody ImageGenerationDto.Request dto) {
        return service.generateImage(authId, dto);
    }
}
