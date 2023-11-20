package com.liberty52.auth.service.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NoticeCreateRequestDto {

    @NotEmpty
    @Size(max = 50)
    String title;

    @NotEmpty
    @Size(max = 10000)
    String content;

    @NotNull
    boolean commentable;

    public static NoticeCreateRequestDto create(String title, String content, boolean commentable) {
        return new NoticeCreateRequestDto(title, content, commentable);
    }
}
