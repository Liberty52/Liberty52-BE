package com.liberty52.auth.service.applicationservice;

import com.liberty52.auth.service.controller.dto.NoticeCommentRequestDto;
import com.liberty52.auth.service.entity.NoticeComment;

public interface NoticeCommentCreateService {
    NoticeComment createNoticeComment(String writerId, String noticeId, NoticeCommentRequestDto requestDto);
}
