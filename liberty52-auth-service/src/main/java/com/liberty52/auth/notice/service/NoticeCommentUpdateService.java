package com.liberty52.auth.notice.service;

import com.liberty52.auth.notice.entity.NoticeComment;
import com.liberty52.auth.notice.web.dto.NoticeCommentRequestDto;

public interface NoticeCommentUpdateService {
    NoticeComment updateNoticeComment(String userId, String noticeId, String commentId, NoticeCommentRequestDto requestDto);
}
