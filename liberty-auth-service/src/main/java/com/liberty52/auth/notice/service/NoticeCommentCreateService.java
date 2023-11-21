package com.liberty52.auth.notice.service;

import com.liberty52.auth.notice.entity.NoticeComment;
import com.liberty52.auth.notice.web.dto.NoticeCommentRequestDto;

public interface NoticeCommentCreateService {
    NoticeComment createNoticeComment(String writerId, String noticeId, NoticeCommentRequestDto requestDto);
}
