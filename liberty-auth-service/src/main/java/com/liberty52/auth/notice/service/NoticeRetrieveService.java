package com.liberty52.auth.notice.service;

import com.liberty52.auth.notice.web.dto.NoticeDetailResponse;
import com.liberty52.auth.notice.web.dto.NoticeRetrieveResponse;
import org.springframework.data.domain.Pageable;

public interface NoticeRetrieveService {

    NoticeRetrieveResponse retrieveUserNotice(Pageable pageable);

    NoticeDetailResponse retrieveUserNoticeDetail(String noticeId);

    NoticeRetrieveResponse retrieveNoticesByAdmin(String role, Pageable pageable);

    NoticeDetailResponse retrieveNoticeDetailByAdmin(String role, String noticeId);

}
