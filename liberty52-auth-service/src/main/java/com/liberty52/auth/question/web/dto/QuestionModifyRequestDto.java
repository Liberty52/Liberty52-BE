package com.liberty52.auth.question.web.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class QuestionModifyRequestDto {
    @NotEmpty
    @Size(max = 50)
    String title;

    @NotEmpty
    @Size(max = 10000)
    String content;

    public static QuestionModifyRequestDto createForTest(String mTitle, String mContent) {
        return new QuestionModifyRequestDto(mTitle, mContent);
    }
}
