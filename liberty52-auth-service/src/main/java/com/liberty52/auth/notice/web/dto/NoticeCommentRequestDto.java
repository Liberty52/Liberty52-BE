package com.liberty52.auth.notice.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class NoticeCommentRequestDto {
    @NotBlank
    private String content;
}
