package com.liberty52.auth.notice.entity;

import com.liberty52.auth.user.entity.Auth;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "noticeComment")
@Getter
@NoArgsConstructor
public class NoticeComment {
    @Id
    private String id = UUID.randomUUID().toString();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Auth writer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Notice notice;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = true, insertable = false)
    private LocalDateTime updatedAt;

    @Builder
    private NoticeComment(Notice notice, Auth writer, String content) {
        this.notice=notice;
        this.writer=writer;
        this.content=content;
    }

    public void modifyContent(String content){
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }
}
