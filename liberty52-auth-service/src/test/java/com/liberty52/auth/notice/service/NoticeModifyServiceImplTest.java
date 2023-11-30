package com.liberty52.auth.notice.service;

import com.liberty52.auth.global.exception.external.forbidden.InvalidAdminRoleException;
import com.liberty52.auth.global.exception.internal.InvalidNoticeContentException;
import com.liberty52.auth.global.exception.internal.InvalidNoticeTitleException;
import com.liberty52.auth.notice.entity.Notice;
import com.liberty52.auth.notice.repository.NoticeRepository;
import com.liberty52.auth.notice.web.dto.NoticeModifyRequestDto;
import com.liberty52.auth.user.entity.Role;
import com.liberty52.common.exception.external.notfound.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;
import java.util.UUID;

@SpringBootTest
class NoticeModifyServiceImplTest {
    @Autowired
    private NoticeModifyService noticeModifyService;
    @Autowired
    private NoticeRepository noticeRepository;

    String noticeId;
    boolean originCommentable;

    @BeforeEach
    void beforeEach() {
        Notice notice = noticeRepository.findAll().get(0);
        noticeId = notice.getId();
        originCommentable = notice.isCommentable();
    }

    @Test
    void basicPath() {
        String newTitle = UUID.randomUUID().toString();
        String newContent = UUID.randomUUID().toString();
        boolean newCommentable = !originCommentable;

        noticeModifyService.modifyNoticeByAdmin(Role.ADMIN.name(), noticeId, NoticeModifyRequestDto.builder().title(newTitle).content(newContent).commentable(newCommentable).build());

        Notice notice = noticeRepository.findById(noticeId).get();
        Assertions.assertEquals(newTitle, notice.getTitle());
        Assertions.assertEquals(newContent, notice.getContent());
        Assertions.assertEquals(newCommentable, notice.isCommentable());
    }

    @Test
    void InvalidAdminRoleException() {
        Assertions.assertThrows(InvalidAdminRoleException.class,
                () -> noticeModifyService.modifyNoticeByAdmin(UUID.randomUUID().toString(), noticeId, NoticeModifyRequestDto.builder().build()));
    }

    @Test
    void resourceNotFound() {
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> noticeModifyService.modifyNoticeByAdmin(Role.ADMIN.name(), UUID.randomUUID().toString(), NoticeModifyRequestDto.builder().build()));
    }

    @Test
    void InvalidNoticeTitle() {
        Assertions.assertThrows(InvalidNoticeTitleException.class,
                () -> noticeModifyService.modifyNoticeByAdmin(
                        Role.ADMIN.name(),
                        noticeId,
                        NoticeModifyRequestDto.builder()
                                .title("?".repeat(Notice.TITLE_MAX_LENGTH + 1))
                                .content(randomContent())
                                .commentable(true)
                                .build()
                ));
    }

    @Test
    void InvalidNoticeContent() {
        Assertions.assertThrows(InvalidNoticeContentException.class,
                () -> noticeModifyService.modifyNoticeByAdmin(
                        Role.ADMIN.name(),
                        noticeId,
                        NoticeModifyRequestDto.builder()
                                .title(randomTitle())
                                .content("?".repeat(Notice.CONTENT_MAX_LENGTH + 1))
                                .commentable(true)
                                .build()
                ));
    }

    private String randomTitle() {
        return randomString(Notice.TITLE_MIN_LENGTH, Notice.TITLE_MAX_LENGTH);
    }

    private String randomContent() {
        return randomString(Notice.CONTENT_MIN_LENGTH, Notice.CONTENT_MAX_LENGTH);
    }

    private String randomString(int min, int max) {
        return "?".repeat(new Random().nextInt(min, max));
    }
}
