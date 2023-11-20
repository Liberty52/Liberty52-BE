package com.liberty52.auth.service.applicationservice;

import com.liberty52.auth.global.exception.external.forbidden.InvalidAdminRoleException;
import com.liberty52.auth.global.exception.external.notfound.NoticeNotFoundById;
import com.liberty52.auth.service.applicationservice.impl.NoticeRetrieveServiceImpl;
import com.liberty52.auth.service.controller.dto.NoticeDetailResponse;
import com.liberty52.auth.service.controller.dto.NoticeRetrieveResponse;
import com.liberty52.auth.service.entity.Notice;
import com.liberty52.auth.service.repository.NoticeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class NoticeRetrieveServiceImplTest {

    @Mock
    private NoticeRepository noticeRepository;
    @InjectMocks
    private NoticeRetrieveServiceImpl noticeRetrieveService;

    private final String ROLE_ADMIN = "ADMIN";
    private final String ROLE_USER = "USER";

    @Test
    void retrieveNoticesByAdmin() {
        // given
        int N = 20;
        List<Notice> givenNotices = new ArrayList<>();
        for (int i = 1; i <= N; i++) {
            givenNotices.add(Notice.builder()
                            .title("notice-"+i)
                            .content("content-"+i)
                            .commentable(false).build());
        }
        given(noticeRepository.findAllByOrderByCreatedAtDesc(any()))
                .willReturn(new PageImpl<>(givenNotices.subList(0, 10), PageRequest.of(0, 10), givenNotices.size()));

        // when
        NoticeRetrieveResponse response = noticeRetrieveService.retrieveNoticesByAdmin(ROLE_ADMIN, PageRequest.of(0, 10));

        // then
        Assertions.assertNotNull(response);
        Assertions.assertEquals(10, response.getContents().size());
        Assertions.assertEquals(1, response.getStartPage());
        Assertions.assertEquals(1, response.getCurrentPage());
        Assertions.assertEquals(2, response.getTotalPage());
        Assertions.assertEquals(N, response.getTotalCount());
    }

    @Test
    void test_retrieveNoticesByAdmin_when_user_throw_InvalidAdminRoleException() {
        Assertions.assertThrows(
                InvalidAdminRoleException.class,
                () -> noticeRetrieveService.retrieveNoticesByAdmin(ROLE_USER, PageRequest.of(0, 10))
        );
    }

    @Test
    void retrieveNoticeDetailByAdmin() {
        // given
        given(noticeRepository.findById(anyString()))
                .willReturn(Optional.of(Notice.builder()
                        .title("notice")
                        .content("content")
                        .commentable(false).build()));
        // when
        NoticeDetailResponse response = noticeRetrieveService.retrieveNoticeDetailByAdmin(ROLE_ADMIN, anyString());
        // then
        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.getNoticeId().isBlank());
        Assertions.assertEquals("notice", response.getTitle());
        Assertions.assertEquals("content", response.getContent());
        Assertions.assertFalse(response.isCommentable());
        Assertions.assertFalse(response.getCreatedAt().isBlank());
    }

    @Test
    void test_retrieveNoticeDetailByAdmin_when_user_throw_InvalidAdminRoleException() {
        final String noticeId = "NOTICE-000";
        Assertions.assertThrows(
                InvalidAdminRoleException.class,
                () -> noticeRetrieveService.retrieveNoticeDetailByAdmin(ROLE_USER, noticeId)
        );
    }

    @Test
    void test_retrieveNoticeDetailByAdmin_when_wrongNoticeId_throw_NoticeNotFoundById() {
        final String noticeId = "WRONG_NOTICE_ID";
        Assertions.assertThrows(
                NoticeNotFoundById.class,
                () -> noticeRetrieveService.retrieveNoticeDetailByAdmin(ROLE_ADMIN, noticeId)
        );
    }

}