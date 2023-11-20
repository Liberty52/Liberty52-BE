package com.liberty52.auth.service.applicationservice;


import com.liberty52.auth.global.exception.external.notfound.NoticeNotFoundById;
import com.liberty52.auth.service.applicationservice.impl.NoticeCommentRetrieveServiceImpl;
import com.liberty52.auth.service.entity.Notice;
import com.liberty52.auth.service.entity.NoticeComment;
import com.liberty52.auth.service.repository.NoticeCommentRepository;
import com.liberty52.auth.service.repository.NoticeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NoticeCommentRetrieveServiceMockTest {
    @InjectMocks
    private NoticeCommentRetrieveServiceImpl noticeCommentRetrieveService;
    @Mock
    private NoticeRepository noticeRepository;
    @Mock
    private NoticeCommentRepository noticeCommentRepository;

    @Test
    public void 공지사항댓글조회_성공() {
        //Given
        String noticeId = "NOTICE-001";
        Notice mockNotice = mock(Notice.class);
        NoticeComment mockNoticeComment1 = mock(NoticeComment.class);
        NoticeComment mockNoticeComment2 = mock(NoticeComment.class);
        Pageable pageable = PageRequest.of(0, 5);
        Page<NoticeComment> mockPage = new PageImpl<>(List.of(mockNoticeComment1, mockNoticeComment2));

        when(noticeRepository.findById(noticeId)).thenReturn(Optional.of(mockNotice));
        when(noticeCommentRepository.findAllByNoticeId(eq(noticeId), any(Pageable.class))).thenReturn(mockPage);

        //When
        Page<NoticeComment> resultPage = noticeCommentRetrieveService.retrieveNoticeComment(noticeId, pageable);

        //Then
        assertNotNull(resultPage);
        assertEquals(2, resultPage.getTotalElements());
    }

    @Test
    void 공지사항댓글조회_실패_없는공지사항() {
        //Given
        String wrongNoticeId = "wrongNoticeId";
        Pageable mockPageable = mock(Pageable.class);
        //When&Then
        Assertions.assertThrows(NoticeNotFoundById.class, () -> noticeCommentRetrieveService.retrieveNoticeComment("wrong_noticeId",mockPageable));
    }
}
