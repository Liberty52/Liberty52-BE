package com.liberty52.auth.service.applicationservice;

import com.liberty52.auth.service.controller.dto.NoticeCommentRequestDto;
import com.liberty52.auth.service.entity.NoticeComment;

public interface NoticeCommentUpdateService {
    NoticeComment updateNoticeComment(String userId, String noticeId, String commentId, NoticeCommentRequestDto requestDto);
}
