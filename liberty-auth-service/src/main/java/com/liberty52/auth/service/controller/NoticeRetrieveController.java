package com.liberty52.auth.service.controller;

import com.liberty52.auth.service.applicationservice.NoticeRetrieveService;
import com.liberty52.auth.service.controller.dto.NoticeDetailResponse;
import com.liberty52.auth.service.controller.dto.NoticeRetrieveResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class NoticeRetrieveController {

    private final NoticeRetrieveService noticeRetrieveService;

    @GetMapping("/notices")
    public ResponseEntity<NoticeRetrieveResponse> retrieveUserNotice(Pageable pageable){
        NoticeRetrieveResponse response = noticeRetrieveService.retrieveUserNotice(
                pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/notices/{noticeId}")
    public ResponseEntity<NoticeDetailResponse> retrieveUserNoticeDetail(@PathVariable("noticeId") String noticeId){
        return ResponseEntity.ok(noticeRetrieveService.retrieveUserNoticeDetail(noticeId));
    }

    @GetMapping("/admin/notices")
    @ResponseStatus(HttpStatus.OK)
    public NoticeRetrieveResponse retrieveNoticesByAdmin(
            @RequestHeader("LB-Role") String role,
            Pageable pageable
    ) {
        return noticeRetrieveService.retrieveNoticesByAdmin(role, pageable);
    }

    @GetMapping("/admin/notices/{noticeId}")
    @ResponseStatus(HttpStatus.OK)
    public NoticeDetailResponse retrieveNoticeDetailByAdmin(
            @RequestHeader("LB-Role") String role,
            @PathVariable String noticeId
    ) {
        return noticeRetrieveService.retrieveNoticeDetailByAdmin(role, noticeId);
    }

}
