package com.liberty52.auth.service.controller;

import com.liberty52.auth.service.applicationservice.QuestionDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class QuestionDeleteController {

    private final QuestionDeleteService questionDeleteService;

    @DeleteMapping("/questions/{questionId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteQuestion(@RequestHeader(HttpHeaders.AUTHORIZATION) String writerId, @PathVariable String questionId) {
        questionDeleteService.deleteQuestion(writerId, questionId);
    }
}
