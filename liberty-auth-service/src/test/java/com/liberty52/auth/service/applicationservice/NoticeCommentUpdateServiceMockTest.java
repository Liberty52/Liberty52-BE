package com.liberty52.auth.service.applicationservice;

import com.liberty52.auth.global.exception.external.notfound.NoticeCommentNotFoundById;
import com.liberty52.auth.service.applicationservice.impl.NoticeCommentUpdateServiceImpl;
import com.liberty52.auth.service.controller.dto.NoticeCommentRequestDto;
import com.liberty52.auth.service.entity.Auth;
import com.liberty52.auth.service.entity.Notice;
import com.liberty52.auth.service.entity.NoticeComment;
import com.liberty52.auth.service.repository.AuthRepository;
import com.liberty52.auth.service.repository.NoticeCommentRepository;
import com.liberty52.auth.service.repository.NoticeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NoticeCommentUpdateServiceMockTest {
    @InjectMocks
    private NoticeCommentUpdateServiceImpl noticeCommentUpdateService;
    @Mock
    private AuthRepository authRepository;
    @Mock
    private NoticeRepository noticeRepository;
    @Mock
    private NoticeCommentRepository noticeCommentRepository;
    private final String testNoticeId = "NOTICE-001";
    private final String testWriterId = "TESTER-001";
    private final String testCommentId = "COMMENT-001";

    @Test
    void 공지사항댓글수정_성공() {
        //Given
        Notice mockNotice = mock(Notice.class);
        Auth mockWriter = mock(Auth.class);
        NoticeCommentRequestDto mockRequestDto = mock(NoticeCommentRequestDto.class);
        NoticeComment mockPreviousNoticeComment = mock(NoticeComment.class);
        NoticeComment mockNewNoticeComment = NoticeComment.builder()
                .content("newContent")
                .build();
        when(authRepository.findById(anyString())).thenReturn(Optional.of(mockWriter));
        when(noticeRepository.findById(anyString())).thenReturn(Optional.of(mockNotice));
        when(noticeCommentRepository.findById(anyString())).thenReturn(Optional.of(mockPreviousNoticeComment));
        when(mockPreviousNoticeComment.getWriter()).thenReturn(mockWriter);
        when(mockPreviousNoticeComment.getWriter().getId()).thenReturn(testWriterId);
        when(noticeCommentRepository.save(any(NoticeComment.class))).thenReturn(mockNewNoticeComment);


        //When
        NoticeComment updatedNoticeComment = noticeCommentUpdateService.updateNoticeComment(testWriterId, testNoticeId, testCommentId, mockRequestDto);

        //Then
        assertEquals(mockNewNoticeComment.getContent(),"newContent");
    }

    @Test
    void 공지사항댓글수정_실패_없는댓글() {
        //Given
        Auth mockWriter = mock(Auth.class);
        NoticeCommentRequestDto mockRequestDto = mock(NoticeCommentRequestDto.class);
        Notice mockNotice = mock(Notice.class);
        when(authRepository.findById(anyString())).thenReturn(Optional.of(mockWriter));
        when(noticeRepository.findById(anyString())).thenReturn(Optional.of(mockNotice));

        //When&Then
        Assertions.assertThrows(NoticeCommentNotFoundById.class, () -> noticeCommentUpdateService.updateNoticeComment(testWriterId, testNoticeId, "wrong_noticeId", mockRequestDto));
    }

}
