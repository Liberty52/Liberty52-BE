package com.liberty52.auth.global.exception.external.notfound;

public class NoticeCommentNotFoundById extends ResourceNotFoundException{
    public NoticeCommentNotFoundById(String commentId) {
        super("NoticeComment", "id", commentId);
    }
}
