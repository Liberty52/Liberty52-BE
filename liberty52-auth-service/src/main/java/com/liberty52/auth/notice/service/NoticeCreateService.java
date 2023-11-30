package com.liberty52.auth.notice.service;

import com.liberty52.auth.notice.web.dto.NoticeCreateRequestDto;

public interface NoticeCreateService {
    void createNoticeByAdmin(String role, NoticeCreateRequestDto dto);
}
