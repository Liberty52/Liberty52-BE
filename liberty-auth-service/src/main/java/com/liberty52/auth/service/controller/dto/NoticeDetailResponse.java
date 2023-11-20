package com.liberty52.auth.service.controller.dto;

import com.liberty52.auth.service.entity.Notice;
import lombok.Data;

@Data
public class NoticeDetailResponse {

    private String noticeId;
    private String title;
    private String content;
    private String createdAt;
    private boolean commentable;

    public NoticeDetailResponse(Notice notice) {
        noticeId = notice.getId();
        title = notice.getTitle();
        content = notice.getContent();
        createdAt = notice.getCreatedAt().toLocalDate().toString();
        commentable = notice.isCommentable();
    }
}
