package com.liberty52.auth.question.entity;

import com.liberty52.auth.global.exception.internal.InvalidQuestionContentException;
import com.liberty52.auth.global.exception.internal.InvalidQuestionTitleException;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter(AccessLevel.PRIVATE)
@Table(name = "question")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question {
    @Id
    private String id = UUID.randomUUID().toString();
    @Enumerated(EnumType.STRING)
    private QuestionStatus status = QuestionStatus.WAITING;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    @Column(nullable = false)
    private String writerId;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    @OneToOne(mappedBy = "question", cascade = CascadeType.ALL)
    private QuestionReply questionReply;

    public static final int TITLE_MAX_LENGTH = 50;
    public static final int TITLE_MIN_LENGTH = 1;
    public static final int CONTENT_MAX_LENGTH = 10000;
    public static final int CONTENT_MIN_LENGTH = 1;

    @Builder
    private Question(String title, String content, String writerId) {
        setTitle(title);
        setContent(content);
        this.writerId = writerId;
    }

    public static Question create(String title, String content, String writerId) {
        Question q = builder().title(title).content(content).writerId(writerId).build();
        q.validTitle();
        q.validContent();
        return q;
    }

    private void validContent() {
        if (content.length() > CONTENT_MAX_LENGTH || content.length() < CONTENT_MIN_LENGTH) {
            throw new InvalidQuestionContentException();
        }
    }

    private void validTitle() {
        if (title.length() > TITLE_MAX_LENGTH || title.length() < TITLE_MIN_LENGTH) {
            throw new InvalidQuestionTitleException();
        }
    }

    public void modify(String title, String content) throws InvalidQuestionContentException, InvalidQuestionTitleException {
        setTitle(title);
        setContent(content);
    }

    private void setTitle(String title) {
        this.title = title;
        validTitle();
    }

    private void setContent(String content) {
        this.content = content;
        validContent();
    }

    public void associate(QuestionReply questionReply){
        this.questionReply = questionReply;
        this.status = QuestionStatus.DONE;
    }

    public void removeReply() {
        this.questionReply = null;
    }
}
