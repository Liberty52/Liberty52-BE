package com.liberty52.auth.service.controller;

import com.liberty52.auth.service.applicationservice.QuestionModifyService;
import com.liberty52.auth.service.controller.dto.QuestionModifyRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class QuestionModifyController {

    private final QuestionModifyService questionModifyService;

    @PutMapping("/questions/{questionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modifyQuestion(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String writerId,
            @PathVariable String questionId,
            @Validated @RequestBody QuestionModifyRequestDto dto) {
        questionModifyService.modifyQuestion(writerId, questionId, dto);
    }
}
