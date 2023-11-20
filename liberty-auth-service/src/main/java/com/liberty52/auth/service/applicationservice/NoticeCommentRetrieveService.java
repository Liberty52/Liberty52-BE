package com.liberty52.auth.service.applicationservice;

import com.liberty52.auth.service.entity.NoticeComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeCommentRetrieveService {
    Page<NoticeComment> retrieveNoticeComment(String noticeId, Pageable pageable);
}
