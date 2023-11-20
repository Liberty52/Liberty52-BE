package com.liberty52.auth.service.applicationservice;

import com.liberty52.auth.global.exception.external.forbidden.NotYourNoticeCommentException;
import com.liberty52.auth.service.applicationservice.impl.NoticeCommentDeleteServiceImpl;
import com.liberty52.auth.service.entity.Auth;
import com.liberty52.auth.service.entity.Notice;
import com.liberty52.auth.service.entity.NoticeComment;
import com.liberty52.auth.service.entity.Role;
import com.liberty52.auth.service.repository.AuthRepository;
import com.liberty52.auth.service.repository.NoticeCommentRepository;
import com.liberty52.auth.service.repository.NoticeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NoticeCommentDeleteServiceMockTest {
    @InjectMocks
    private NoticeCommentDeleteServiceImpl service;

    @Mock
    private AuthRepository authRepository;

    @Mock
    private NoticeRepository noticeRepository;

    @Mock
    private NoticeCommentRepository noticeCommentRepository;

    @Test
    public void 공지사항댓글삭제_성공() {
        //Given
        String userId = "TESTER-001";
        String noticeId = "NOTICE-001";
        String commentId = "COMMENT-001";

        Auth mockWriter = mock(Auth.class);
        Notice mockNotice = mock(Notice.class);
        NoticeComment mockComment = mock(NoticeComment.class);


        when(authRepository.findById(anyString())).thenReturn(Optional.of(mockWriter));
        when(noticeRepository.findById(anyString())).thenReturn(Optional.of(mockNotice));
        when(noticeCommentRepository.findById(anyString())).thenReturn(Optional.of(mockComment));
        when(mockWriter.getId()).thenReturn(userId);
        when(mockComment.getWriter()).thenReturn(mockWriter);

        //When
        service.deleteNoticeComment(userId, noticeId, commentId);

        //Then
        Mockito.verify(noticeCommentRepository, Mockito.times(1)).delete(mockComment);
    }

    @Test
    public void 관리자_공지항댓글삭제_성공() {
        //Given
        String role = String.valueOf(Role.ADMIN);
        String noticeId = "NOTICE-001";
        String commentId = "COMMENT-001";

        Notice mockNotice = mock(Notice.class);
        NoticeComment mockComment = mock(NoticeComment.class);

        when(noticeRepository.findById(anyString())).thenReturn(Optional.of(mockNotice));
        when(noticeCommentRepository.findById(anyString())).thenReturn(Optional.of(mockComment));

        //When
        service.deleteNoticeCommentByAdmin(role, noticeId, commentId);

        //Then
        Mockito.verify(noticeCommentRepository, Mockito.times(1)).delete(mockComment);
    }

    @Test
    public void 공지사항댓글삭제_실패_나의댓글아님() {
        //Given
        String userId = "TESTER-002";
        String noticeId = "NOTICE-001";
        String writerId = "TESTER-001";
        String commentId = "COMMENT-001";

        Auth mockWriter = mock(Auth.class);
        Notice mockNotice = mock(Notice.class);
        NoticeComment mockComment = mock(NoticeComment.class);

        when(authRepository.findById(anyString())).thenReturn(Optional.of(mockWriter));
        when(noticeRepository.findById(anyString())).thenReturn(Optional.of(mockNotice));
        when(noticeCommentRepository.findById(anyString())).thenReturn(Optional.of(mockComment));
        when(mockComment.getWriter()).thenReturn(mockWriter);
        when(mockComment.getWriter().getId()).thenReturn(writerId);


        //When&Then
        assertThrows(NotYourNoticeCommentException.class, () -> service.deleteNoticeComment(userId, noticeId, commentId));
    }
}
