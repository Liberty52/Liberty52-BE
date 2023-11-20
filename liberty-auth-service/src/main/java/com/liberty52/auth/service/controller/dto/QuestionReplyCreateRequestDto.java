package com.liberty52.auth.service.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class QuestionReplyCreateRequestDto {

    @NotEmpty
    String questionId;

    @NotEmpty
    @Size(max = 10000)
    String content;

    public static QuestionReplyCreateRequestDto create(String questionId, String content) {
        return new QuestionReplyCreateRequestDto(questionId, content);
    }
}
