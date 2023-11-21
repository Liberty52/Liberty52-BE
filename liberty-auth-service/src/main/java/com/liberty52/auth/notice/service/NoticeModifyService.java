package com.liberty52.auth.notice.service;

import com.liberty52.auth.notice.web.dto.NoticeModifyRequestDto;

public interface NoticeModifyService {
    void modifyNoticeByAdmin(String role, String noticeId, NoticeModifyRequestDto dto);
}
