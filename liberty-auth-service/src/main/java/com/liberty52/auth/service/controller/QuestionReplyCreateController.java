package com.liberty52.auth.service.controller;

import com.liberty52.auth.service.applicationservice.QuestionReplyCreateService;
import com.liberty52.auth.service.controller.dto.QuestionReplyCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class QuestionReplyCreateController {

    private final QuestionReplyCreateService questionReplyCreateService;

    @PostMapping("/admin/questionReplies")
    @ResponseStatus(HttpStatus.CREATED)
    public void createQuestionReplyByAdmin(@RequestHeader(HttpHeaders.AUTHORIZATION) String adminId, @RequestHeader("LB-Role") String role, @Validated @RequestBody QuestionReplyCreateRequestDto dto) {
        questionReplyCreateService.createQuestionReplyByAdmin(adminId, role, dto);
    }
}
