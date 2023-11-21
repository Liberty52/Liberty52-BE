package com.liberty52.auth.service.applicationservice;

import com.liberty52.auth.notice.entity.Notice;
import com.liberty52.auth.notice.repository.NoticeRepository;
import com.liberty52.auth.notice.service.NoticeCreateService;
import com.liberty52.auth.notice.web.dto.NoticeCreateRequestDto;
import com.liberty52.auth.user.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class NoticeCreateServiceTest {

    @Autowired
    NoticeRepository noticeRepository;

    @Autowired
    NoticeCreateService noticeCreateService;

    @Test
    void 공지추가() {
        String title = "테스트제목";
        NoticeCreateRequestDto dto = NoticeCreateRequestDto.create(title, "내용", true);
        noticeCreateService.createNoticeByAdmin(Role.ADMIN.name(), dto);

        Notice notice = noticeRepository.findByTitle(title).orElse(null);
        assertThat(notice.getTitle()).isEqualTo(title);
        assertThat(notice.getContent()).isEqualTo("내용");
        assertThat(notice.isCommentable()).isEqualTo(true);
        assertThat(notice.getCreatedAt().toLocalDate()).isEqualTo(LocalDate.now());
    }
}
