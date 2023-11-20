package com.liberty52.auth.service.applicationservice;

import com.liberty52.auth.service.controller.dto.NoticeModifyRequestDto;

public interface NoticeModifyService {
    void modifyNoticeByAdmin(String role, String noticeId, NoticeModifyRequestDto dto);
}
