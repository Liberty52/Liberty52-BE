package com.liberty52.auth.notice.entity;

import com.liberty52.auth.global.exception.internal.InvalidNoticeContentException;
import com.liberty52.auth.global.exception.internal.InvalidNoticeTitleException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter(AccessLevel.PRIVATE)
@Table(name = "notice")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice {
    @Id
    private String id = UUID.randomUUID().toString();
    @Column(name = "title", nullable = false, columnDefinition = "TEXT")
    private String title;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    @Column(name = "commentable", nullable = false)
    private boolean commentable;

    public static final int TITLE_MAX_LENGTH = 50;
    public static final int TITLE_MIN_LENGTH = 1;
    public static final int CONTENT_MAX_LENGTH = 10000;
    public static final int CONTENT_MIN_LENGTH = 1;

    @Builder
    private Notice(String title, String content, boolean commentable) {
        setTitle(title);
        setContent(content);
        setCommentable(commentable);
    }

    public static Notice create(String title, String content, boolean commentable) throws InvalidNoticeContentException, InvalidNoticeTitleException{
        return builder().title(title).content(content).commentable(commentable).build();
    }

    private void setTitle(String title) {
        this.title = title;
        validTitle();
    }

    private void validTitle() {
        if(this.title.length() > TITLE_MAX_LENGTH || this.title.length() < TITLE_MIN_LENGTH)
            throw new InvalidNoticeTitleException();
    }

    private void setContent(String content) {
        this.content = content;
        validContent();
    }

    private void validContent() {
        if(this.content.length() > CONTENT_MAX_LENGTH || this.content.length() < CONTENT_MIN_LENGTH)
            throw new InvalidNoticeContentException();
    }

    public void modify(String title, String content, boolean commentable) throws InvalidNoticeTitleException, InvalidNoticeContentException{
        setTitle(title);
        setContent(content);
        setCommentable(commentable);
    }
}
