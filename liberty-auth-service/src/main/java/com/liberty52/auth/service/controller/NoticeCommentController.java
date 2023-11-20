package com.liberty52.auth.service.controller;

import com.liberty52.auth.service.applicationservice.NoticeCommentCreateService;
import com.liberty52.auth.service.applicationservice.NoticeCommentDeleteService;
import com.liberty52.auth.service.applicationservice.NoticeCommentRetrieveService;
import com.liberty52.auth.service.applicationservice.NoticeCommentUpdateService;
import com.liberty52.auth.service.controller.dto.NoticeCommentRequestDto;
import com.liberty52.auth.service.controller.dto.NoticeCommentResponseDto;
import com.liberty52.auth.service.entity.NoticeComment;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class NoticeCommentController {
    private final NoticeCommentCreateService noticeCommentCreateService;
    private final NoticeCommentRetrieveService noticeCommentRetrieveService;
    private final NoticeCommentUpdateService noticeCommentUpdateService;
    private final NoticeCommentDeleteService noticeCommentDeleteService;

    @Operation(summary = "공지사항 댓글 생성", description = "공지사항에 대한 댓글을 생성합니다.")
    @PostMapping("/notices/{noticeId}/comments")
    public ResponseEntity<NoticeCommentResponseDto> createNoticeComment(@RequestHeader(HttpHeaders.AUTHORIZATION) String userId,
                                                                        @PathVariable String noticeId,
                                                                        @RequestBody @Valid NoticeCommentRequestDto requestDto){
        NoticeComment resultEntity = noticeCommentCreateService.createNoticeComment(userId, noticeId, requestDto);
        NoticeCommentResponseDto responseDto = new NoticeCommentResponseDto(resultEntity, userId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @Operation(summary = "공지사항 댓글 조회", description = "공지사항에 대한 댓글을 조회합니다.")
    @GetMapping("/notices/{noticeId}/comments")
    public ResponseEntity<Page<NoticeCommentResponseDto>> retrieveNoticeComment(@RequestHeader(name = "Authorization", required = false) String userId,
                                                                                @PathVariable String noticeId,
                                                                                Pageable pageable){
        Page<NoticeComment> resultEntityPage = noticeCommentRetrieveService.retrieveNoticeComment(noticeId, pageable);
        Page<NoticeCommentResponseDto> responseDtoPage = NoticeCommentResponseDto.convertEntityPageToDtoPage(resultEntityPage, userId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDtoPage);
    }

    @Operation(summary = "공지사항 댓글 수정", description = "공지사항에 대한 댓글을 수정합니다.")
    @PatchMapping("/notices/{noticeId}/comments/{commentId}")
    public ResponseEntity<NoticeCommentResponseDto> updateNoticeComment(@RequestHeader(HttpHeaders.AUTHORIZATION) String userId,
                                                                        @PathVariable String noticeId,
                                                                        @PathVariable String commentId,
                                                                        @RequestBody @Valid NoticeCommentRequestDto requestDto){
        NoticeComment resultEntity = noticeCommentUpdateService.updateNoticeComment(userId, noticeId, commentId, requestDto);
        NoticeCommentResponseDto responseDto = new NoticeCommentResponseDto(resultEntity, userId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @Operation(summary = "공지사항 댓글 삭제", description = "공지사항에 대한 댓글을 삭제합니다.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/notices/{noticeId}/comments/{commentId}")
    public void deleteNoticeComment(@RequestHeader(HttpHeaders.AUTHORIZATION) String userId,
                                    @PathVariable String noticeId,
                                    @PathVariable String commentId){
        noticeCommentDeleteService.deleteNoticeComment(userId, noticeId, commentId);
    }

    @Operation(summary = "관리자의 공지사항 댓글 삭제", description = "관리자가 공지사항에 대한 댓글을 삭제합니다.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("admin/notices/{noticeId}/comments/{commentId}")
    public void deleteNoticeCommentByAdmin(@RequestHeader("LB-Role") String role,
                                           @PathVariable String noticeId,
                                           @PathVariable String commentId){
        noticeCommentDeleteService.deleteNoticeCommentByAdmin(role, noticeId, commentId);
    }




}
