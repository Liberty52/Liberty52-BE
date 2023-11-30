package com.liberty52.auth.question.web.rest;

import com.liberty52.auth.question.service.QuestionReplyModifyService;
import com.liberty52.auth.question.web.dto.QuestionReplyModifyRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class QuestionReplyModifyController {

    private final QuestionReplyModifyService questionReplyModifyService;

    @PutMapping("/admin/questionReplies/{questionReplyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modifyQuestionReplyByAdmin(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String writerId,
            @RequestHeader("LB-Role") String role,
            @PathVariable String questionReplyId,
            @Validated @RequestBody QuestionReplyModifyRequestDto dto) {
        questionReplyModifyService.modifyQuestionReplyByAdmin(writerId, role, questionReplyId, dto);
    }
}
