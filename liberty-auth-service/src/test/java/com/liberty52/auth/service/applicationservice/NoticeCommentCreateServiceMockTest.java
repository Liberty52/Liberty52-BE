package com.liberty52.auth.service.applicationservice;

import com.liberty52.auth.global.exception.external.notfound.NoticeNotFoundById;
import com.liberty52.auth.notice.entity.Notice;
import com.liberty52.auth.notice.entity.NoticeComment;
import com.liberty52.auth.notice.repository.NoticeCommentRepository;
import com.liberty52.auth.notice.repository.NoticeRepository;
import com.liberty52.auth.notice.service.NoticeCommentCreateServiceImpl;
import com.liberty52.auth.notice.web.dto.NoticeCommentRequestDto;
import com.liberty52.auth.user.entity.Auth;
import com.liberty52.auth.user.repository.AuthRepository;
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
public class NoticeCommentCreateServiceMockTest {
    @InjectMocks
    private NoticeCommentCreateServiceImpl noticeCommentCreateService;

    @Mock
    private NoticeRepository noticeRepository;

    @Mock
    private NoticeCommentRepository noticeCommentRepository;

    @Mock
    private AuthRepository authRepository;

    private final String testNoticeId = "NOTICE-001";

    private final String testWriterId = "TESTER-001";

    @Test
    void 공지사항댓글생성_성공() {
        //Given
        Notice mockNotice = mock(Notice.class);
        Auth mockWriter = mock(Auth.class);
        NoticeCommentRequestDto mockRequestDto = mock(NoticeCommentRequestDto.class);
        String testContent = "testContent";
        NoticeComment mockNoticeComment = NoticeComment.builder()
                .notice(mockNotice)
                .writer(mockWriter)
                .content(testContent)
                .build();
        when(authRepository.findById(anyString())).thenReturn(Optional.of(mockWriter));
        when(noticeRepository.findById(anyString())).thenReturn(Optional.of(mockNotice));
        when(noticeCommentRepository.save(any(NoticeComment.class))).thenReturn(mockNoticeComment);

        //When
        NoticeComment savedNoticeComment = noticeCommentCreateService.createNoticeComment(testWriterId, testNoticeId, mockRequestDto);

        //Then
        assertEquals(mockNotice, savedNoticeComment.getNotice());
        assertEquals(mockWriter, savedNoticeComment.getWriter());
        assertEquals(testContent, savedNoticeComment.getContent());
    }

    @Test
    void 공지사항댓글생성_실패_없는공지사항() {
        //Given
        Auth mockWriter = mock(Auth.class);
        NoticeCommentRequestDto mockRequestDto = mock(NoticeCommentRequestDto.class);
        when(authRepository.findById(anyString())).thenReturn(Optional.of(mockWriter));

        //When&Then
        Assertions.assertThrows(NoticeNotFoundById.class, () -> noticeCommentCreateService.createNoticeComment(testWriterId,"wrong_noticeId",mockRequestDto));
    }




}
