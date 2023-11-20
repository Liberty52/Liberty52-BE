package com.liberty52.auth.service.controller.dto;

import com.liberty52.auth.service.entity.NoticeComment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class NoticeCommentResponseDto {
    private String commentId;
    private String noticeId;
    private String writerId;
    private String writerName;
    private String writerEmail;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isMine = false;

    public NoticeCommentResponseDto(NoticeComment resultEntity) {
        this.commentId = resultEntity.getId();
        this.noticeId = resultEntity.getNotice().getId();
        this.writerId = resultEntity.getWriter().getId();
        this.writerName = resultEntity.getWriter().getName();
        this.writerEmail = resultEntity.getWriter().getEmail();
        this.content = resultEntity.getContent();
        this.createdAt = resultEntity.getCreatedAt();
        this.updatedAt = resultEntity.getUpdatedAt();
    }

    public NoticeCommentResponseDto(NoticeComment resultEntity, String userId) {
        this.commentId = resultEntity.getId();
        this.noticeId = resultEntity.getNotice().getId();
        this.writerId = resultEntity.getWriter().getId();
        this.writerName = resultEntity.getWriter().getName();
        this.writerEmail = resultEntity.getWriter().getEmail();
        this.content = resultEntity.getContent();
        this.createdAt = resultEntity.getCreatedAt();
        this.updatedAt = resultEntity.getUpdatedAt();
        if(userId!=null&&userId.equals(resultEntity.getWriter().getId()))
            this.isMine = true;
    }

    public static Page<NoticeCommentResponseDto> convertEntityPageToDtoPage(Page<NoticeComment> entityPage, String userId) {
        List<NoticeCommentResponseDto> dtoList = entityPage
                .stream()
                .map(resultEntity -> new NoticeCommentResponseDto(resultEntity, userId))
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, entityPage.getPageable(), entityPage.getTotalElements());
    }
}
