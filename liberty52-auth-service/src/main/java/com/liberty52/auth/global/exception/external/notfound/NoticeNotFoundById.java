package com.liberty52.auth.global.exception.external.notfound;

import com.liberty52.common.exception.external.notfound.ResourceNotFoundException;

public class NoticeNotFoundById extends ResourceNotFoundException {

    public NoticeNotFoundById(String noticeId) {
        super("Notice", "id", noticeId);
    }
}
