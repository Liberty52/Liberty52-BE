package com.liberty52.auth.service.controller;

import com.liberty52.auth.service.applicationservice.QuestionCreateService;
import com.liberty52.auth.service.controller.dto.QuestionCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class QuestionCreateController {

    private final QuestionCreateService questionCreateService;

    @PostMapping("/questions")
    @ResponseStatus(HttpStatus.CREATED)
    public void createQuestion(@RequestHeader(HttpHeaders.AUTHORIZATION) String writerId, @Validated @RequestBody QuestionCreateRequestDto dto) {
        questionCreateService.createQuestion(writerId, dto);
    }
}
