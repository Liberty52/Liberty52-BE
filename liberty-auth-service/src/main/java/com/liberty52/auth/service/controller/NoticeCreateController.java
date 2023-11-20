package com.liberty52.auth.service.controller;

import com.liberty52.auth.service.applicationservice.NoticeCreateService;
import com.liberty52.auth.service.controller.dto.NoticeCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class NoticeCreateController {

    private final NoticeCreateService noticeCreateService;

    @PostMapping("/admin/notices")
    @ResponseStatus(HttpStatus.CREATED)
    public void createNoticeByAdmin(@RequestHeader("LB-Role") String role, @Validated @RequestBody NoticeCreateRequestDto dto) {
        noticeCreateService.createNoticeByAdmin(role, dto);
    }
}
