package com.liberty52.auth.notice.web.rest;

import com.liberty52.auth.notice.service.NoticeModifyService;
import com.liberty52.auth.notice.web.dto.NoticeModifyRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class NoticeModifyController {
    private final NoticeModifyService noticeModifyService;

    @PutMapping("/admin/notices/{noticeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modifyNoticeByAdmin(@RequestHeader("LB-Role") String role,
                             @PathVariable String noticeId,
                             @Validated @RequestBody NoticeModifyRequestDto dto) {
        noticeModifyService.modifyNoticeByAdmin(role, noticeId, dto);
    }
}
