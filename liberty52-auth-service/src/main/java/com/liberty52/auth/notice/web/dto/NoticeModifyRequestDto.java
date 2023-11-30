package com.liberty52.auth.notice.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NoticeModifyRequestDto {
    @NotBlank
    @Size(max = 50)
    private String title;
    @NotBlank
    @Size(max = 10000)
    private String content;
    private boolean commentable;
}
