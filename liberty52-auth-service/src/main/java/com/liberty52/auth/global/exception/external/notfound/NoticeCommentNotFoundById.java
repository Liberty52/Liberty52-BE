package com.liberty52.auth.global.exception.external.notfound;

import com.liberty52.common.exception.external.notfound.ResourceNotFoundException;

public class NoticeCommentNotFoundById extends ResourceNotFoundException {
    public NoticeCommentNotFoundById(String commentId) {
        super("NoticeComment", "id", commentId);
    }
}
