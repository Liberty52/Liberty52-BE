package com.liberty52.auth.service.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class QuestionReplyModifyRequestDto {
    @NotEmpty
    @Size(max = 10000)
    String content;
    public static QuestionReplyModifyRequestDto createForTest(String content) {
        return new QuestionReplyModifyRequestDto(content);
    }
}
