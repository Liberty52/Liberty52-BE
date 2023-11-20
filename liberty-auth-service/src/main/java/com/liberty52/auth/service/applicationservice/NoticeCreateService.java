package com.liberty52.auth.service.applicationservice;

import com.liberty52.auth.service.controller.dto.NoticeCreateRequestDto;

public interface NoticeCreateService {
    void createNoticeByAdmin(String role, NoticeCreateRequestDto dto);
}
