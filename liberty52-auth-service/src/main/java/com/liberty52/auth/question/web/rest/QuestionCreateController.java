package com.liberty52.auth.question.web.rest;

import com.liberty52.auth.question.service.QuestionCreateService;
import com.liberty52.auth.question.web.dto.QuestionCreateRequestDto;
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
