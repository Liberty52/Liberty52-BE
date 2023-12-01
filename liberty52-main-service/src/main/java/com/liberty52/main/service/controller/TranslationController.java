package com.liberty52.main.service.controller;

import com.liberty52.main.service.applicationservice.TranslationService;
import com.liberty52.main.service.controller.dto.TranslationDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "번역", description = "텍스트 번역 API를 제공합니다")
@RestController
@RequiredArgsConstructor
public class TranslationController {
    private final TranslationService translationService;

    @Operation(summary = "텍스트 번역", description = "입력된 텍스트를 번역하여 응답합니다.")
    @PostMapping("/texts/translations")
    @ResponseStatus(HttpStatus.CREATED)
    public TranslationDto.Response translateText(@RequestHeader(HttpHeaders.AUTHORIZATION) String authId,
                                                 @Validated @RequestBody TranslationDto.Request dto) {
        return translationService.translateText(authId, dto);
    }
}
